package bot;

import infra.DatabaseController;
import infra.Utils;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommandsController {

    private static final String START_COMMAND_SYMBOL = "/";

    private static final String LIST_OF_COMMANDS = "*/help* - Get help\n" +
            "*/list* - Get the list of available commands\n" +
            "*/notifications* - Get pending notifications\n" +
            "*/delete* - Delete specific or all notification(s)";

    public static SendMessage processCommand(Update update) {
        String text = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();

        switch (text.toLowerCase(Locale.ROOT)) {
            case START_COMMAND_SYMBOL + "start":
                return startCommand(chatId);
            case START_COMMAND_SYMBOL + "help":
                return helpCommand(chatId);
            case START_COMMAND_SYMBOL + "list":
                return listCommand(chatId);
            case START_COMMAND_SYMBOL + "notifications":
                return getNotificationsCommand(userId, chatId);
            case START_COMMAND_SYMBOL + "delete":
                return deleteNotificationsCommand(chatId);
            default:
                return defaultCommand(chatId);
        }
    }

    private static SendMessage deleteNotificationsCommand(long chatId) {
        return null;
    }

    private static SendMessage getNotificationsCommand(long userId, long chatId) {

        List<Document> notifications = DatabaseController.getPendingNotifications(userId, chatId);

        StringBuilder text = new StringBuilder("You have *")
                .append(notifications.size())
                .append("* pending notification(s)");
        try {
        for (Document doc : notifications) {
            text.append("\n=================")
                    .append("\n*From:* ").append(doc.get("forwarded_from_name"))
                    .append("\n*Message:* `").append(doc.get("message"))
                    .append("`\n*Notify at:* ").append(Utils.prettifyDate((Date) doc.get("notifyAt")));
        }} catch (Exception e) {
            e.printStackTrace();
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(text.toString());
        return message;
    }

    private static SendMessage helpCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText("*TBD Help text*");
        return message;
    }

    private static SendMessage listCommand(long chatId) {
        String listMessage = "List of available commands:\n" + LIST_OF_COMMANDS;

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(listMessage);
        return message;
    }

    private static SendMessage startCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText("Hello!\nThis is a bot which can remind you to reply to the message\n" +
                "_Here are the list of available commands:_\n" + LIST_OF_COMMANDS);
        return message;
    }

    private static SendMessage defaultCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Unknown command, try again");
        return message;
    }
}
