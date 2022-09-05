package bot;

import infra.DatabaseController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageController {
    public static SendMessage processMessage(Message message) {

        DatabaseController.saveMessage(message);

        String replyText = String.format("You forwarded message from: %s\nMessage: %s\nDo you want me to remind later?",
                message.getForwardFrom().getFirstName(),
                message.getText());

        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId());
        reply.setText(replyText);
        return reply;
    }
}
