package bot;

import infra.KeyboardGenerator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandsController {

    private static final String START_COMMAND_SYMBOL = "/";

    public static String processCommand(String text) {
        switch (text) {
            case "/hello":
                return "Hello hello";
            case "/mock":
                return "mock";
            default:
                return  "Unknown command, try again";
        }
    }

    public static SendMessage processCommand(Update update) {
        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (text) {
            case START_COMMAND_SYMBOL + "hello":
                return helloCommand(chatId);
            case START_COMMAND_SYMBOL + "start":
                return startCommand(chatId);
            case START_COMMAND_SYMBOL + "buttons":
                return buttonsCommand(chatId);
            default:
                return defaultCommand(chatId);
        }
    }

    private static SendMessage buttonsCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Look what we got");
        HashMap<String, String> buttons = new HashMap<>();
        buttons.put("One", "first_button");
        buttons.put("Two", "second_button");
        buttons.put("Exit", "exit_button");
        message.setReplyMarkup(KeyboardGenerator.createInlineKeyboard(buttons));
        return message;
    }

    private static SendMessage helloCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello hello");
        return message;
    }

    private static SendMessage startCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Let's start then");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Update message text");
        button.setCallbackData("update_msg_text");
        rowInline.add(button);
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    private static SendMessage defaultCommand(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Unknown command, try again");
        return message;
    }
}
