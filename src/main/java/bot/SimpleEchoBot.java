package bot;

import infra.BaseBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SimpleEchoBot extends BaseBot {

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = CommandsController.processCommand(update.getMessage().getText());
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            message.setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
//    private void sendMessage(long chatId) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create("{\"text\":\"Required\",\"disable_web_page_preview\":false,\"disable_notification\":false,\"reply_to_message_id\":null,\"chat_id\":\"136470163\"}", mediaType);
//        Request request = new Request.Builder()
//                .url(String.format("https://api.telegram.org/bot%s/sendMessage", Configuration.getConstants().telegramApiToken()))
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("User-Agent", "Telegram Bot SDK - (https://github.com/irazasyed/telegram-bot-sdk)")
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();
//    }
}
