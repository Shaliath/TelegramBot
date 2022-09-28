package bot;

import database.UsersController;
import infra.BaseBot;
import infra.DatabaseController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends BaseBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message incomingMessage = update.getMessage();
            System.out.println(UsersController.create(incomingMessage.getFrom()));
            System.out.println(UsersController.getByUserId(incomingMessage.getFrom().getId()));
            System.out.println(DatabaseController.checkUser(incomingMessage.getFrom()));
            SendMessage message;
            if (update.getMessage().getForwardFrom() != null) {
                message = MessageController.processMessage(update.getMessage());
            } else {
                message = CommandsController.processCommand(update);
            }

            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            SendMessage message = CallbackController.processCommand(update);

            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
