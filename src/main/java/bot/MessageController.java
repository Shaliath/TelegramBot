package bot;

import infra.DatabaseController;
import infra.KeyboardGenerator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.LinkedHashMap;

public class MessageController {
    public static SendMessage processMessage(Message message) {

//        DatabaseController.saveMessage(message);

        String replyText = String.format("You forwarded message from: %s\nDo you want me to remind later?",
                message.getForwardFrom().getFirstName());

        LinkedHashMap<String, String> buttons = new LinkedHashMap<>();
        buttons.put("Notify in 1 hour", "one_hour");
        buttons.put("Notify in 4 hours", "four_hours");
        buttons.put("Notify in 8 hours", "eight_hours");

        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId());
        reply.setText(replyText);
        reply.setReplyMarkup(KeyboardGenerator.createInlineKeyboard(buttons));
        reply.setReplyToMessageId(message.getMessageId());
        return reply;
    }
}
