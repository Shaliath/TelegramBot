package infra;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class BaseBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return Configuration.getConstants().telegramBotName();
    }

    @Override
    public String getBotToken() {
        return Configuration.getConstants().telegramApiToken();
    }
}
