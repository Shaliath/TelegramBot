package infra;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class DatabaseController {

    private static final String DB_URL = Configuration.getConfig().databaseUrl();
    private static final String DB_NAME = Configuration.getConfig().databaseName();

    public static String checkUser(User user) {
        return checkUserData(user.getFirstName(), user.getLastName(), user.getId(), user.getUserName());
    }

    public static String saveMessage(Message message) {
        return processMessage(message.getFrom().getId(), message.getChatId(), message.getMessageId(), message.getForwardFrom().getId());
    }

    public static String saveMessage(Update update, long time) {
        Message message = update.getCallbackQuery().getMessage().getReplyToMessage();
        return processMessage(message.getFrom().getId(), message.getChatId(), message.getMessageId(), message.getForwardFrom().getId(), message.getForwardFrom().getFirstName(), message.getText(), time);
    }

    private static String checkUserData(String firstName, String lastName, long userId, String username) {
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DB_URL))) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("users");
            long found = collection.countDocuments(Document.parse("{id : " + userId + "}"));
            if (found == 0) {
                Document doc = new Document("first_name", firstName)
                        .append("last_name", lastName)
                        .append("id", userId)
                        .append("username", username);
                collection.insertOne(doc);
                System.out.println("User not exists in database. Written.");
                return "no_exists";
            } else {
                System.out.println("User exists in database.");
                return "exists";
            }
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String processMessage(long userId, long chatId, long messageId, long forwardedFromId) {
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DB_URL))) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("messages");
            Document doc = new Document("user_id", userId)
                    .append("chat_id", chatId)
                    .append("message_id", messageId)
                    .append("forwarded_from_id", forwardedFromId)
                    .append("created", new Date());
            collection.insertOne(doc);
            return doc.get("_id").toString();
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String processMessage(long userId, long chatId, long messageId, long forwardedFromId, String forwardedFromName, String message, long time) {

        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DB_URL))) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("messages");
            Document doc = new Document("user_id", userId)
                    .append("chat_id", chatId)
                    .append("message_id", messageId)
                    .append("forwarded_from_id", forwardedFromId)
                    .append("forwarded_from_name", forwardedFromName)
                    .append("message", message)
                    .append("created", LocalDateTime.now())
                    .append("notifyAt", LocalDateTime.now().plusHours(time))
                    .append("notificationSent", false);
            collection.insertOne(doc);
            return doc.get("_id").toString();
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Document> getPendingNotifications(long userId, long chatId) {
        List<Document> pendingNotifications = new ArrayList<>();
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DB_URL))) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("messages");
            FindIterable<Document> found = collection
                    .find(and(eq("chat_id", userId), eq("notificationSent", false)));

            for (Document doc : found) {
                pendingNotifications.add(doc);
            }

        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
        }
        return pendingNotifications;
    }

}
