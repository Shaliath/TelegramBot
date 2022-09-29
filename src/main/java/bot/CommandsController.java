package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

public class CommandsController {

    private static final String START_COMMAND_SYMBOL = "/";

    private static final String LIST_OF_COMMANDS = "*/help* - Get help\n" +
            "*/list* - Get the list of available commands\n" +
            "*/notifications* - Get pending notifications\n" +
            "*/delete* - Delete specific or all notification(s)";

    public static SendMessage processCommand(Update update) {
        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (text.toLowerCase(Locale.ROOT)) {
            case START_COMMAND_SYMBOL + "start":
                return startCommand(chatId);
            case START_COMMAND_SYMBOL + "help":
                return helpCommand(chatId);
            case START_COMMAND_SYMBOL + "list":
                return listCommand(chatId);
            case START_COMMAND_SYMBOL + "notifications":
                return getNotificationsCommand(chatId);
            case START_COMMAND_SYMBOL + "delete":
                return deleteNotificationsCommand(chatId);
            default:
                return defaultCommand(chatId);
        }
    }

    private static SendMessage deleteNotificationsCommand(long chatId) {
        return null;
    }

    private static SendMessage getNotificationsCommand(long chatId) {
        return null;
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
