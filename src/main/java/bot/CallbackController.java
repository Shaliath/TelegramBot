package bot;

import infra.DatabaseController;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackController {

    public static EditMessageText processCommand(Update update) {
        String callData = update.getCallbackQuery().getData();

        switch (callData) {
            case "one_hour":
                return oneHourCallback(update);
            case "four_hours":
                return fourHoursCallback(update);
            case "eight_hours":
                return eightHoursCallback(update);
            default:
                return defaultCallback(update.getCallbackQuery().getMessage());
        }
    }

    private static EditMessageText oneHourCallback(Update update) {
        return editNotificationMessage(update, 1);
    }

    private static EditMessageText fourHoursCallback(Update update) {
        return editNotificationMessage(update, 4);
    }

    private static EditMessageText eightHoursCallback(Update update) {
        return editNotificationMessage(update, 8);
    }

    private static EditMessageText editNotificationMessage(Update update, long hours) {

        DatabaseController.saveMessage(update, hours);

        EditMessageText message = new EditMessageText();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        message.setText(String.format("You'll be notified in %d hour(s)", hours));
        return message;
    }

    private static EditMessageText defaultCallback(Message callbackMessage) {
        EditMessageText message = new EditMessageText();
        message.setChatId(callbackMessage.getChatId());
        message.setMessageId(callbackMessage.getMessageId());
        message.setText("Unknown callback");
        return message;
    }

}
