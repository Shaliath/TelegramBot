package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class CallbackController {

    public static SendMessage processCommand(Update update) {
        String callData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callData) {
            case "first_button":
                return firstCallback(chatId);
            case "second_button":
                return secondCallback(chatId);
            case "exit_button":
                return exitCallback(chatId);
            default:
                return defaultCallback(chatId);
        }
    }

    private static SendMessage secondCallback(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Русні пизда");
        return message;
    }

    private static SendMessage firstCallback(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Шо по русні?");
        return message;
    }

    private static SendMessage defaultCallback(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Unknown callback");
        return message;
    }

    private static SendMessage exitCallback(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Adios");
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        message.setReplyMarkup(replyKeyboardRemove);
        return message;
    }

}
