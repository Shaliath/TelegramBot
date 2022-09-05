package infra;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardGenerator {

    public static ReplyKeyboardMarkup createReplyKeyboard(String... buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        for (String button : buttons) {
            KeyboardRow rowInline = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(button);
            rowInline.add(keyboardButton);
            keyboardRows.add(rowInline);
        }

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createInlineKeyboard(HashMap<String, String> buttons) {
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        for (Map.Entry<String, String> button : buttons.entrySet()) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(button.getKey());
            keyboardButton.setCallbackData(button.getValue());

            rowInline.add(keyboardButton);
            keyboardRows.add(rowInline);
        }

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

}
