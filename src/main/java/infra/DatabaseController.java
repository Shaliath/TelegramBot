package infra;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;

public class DatabaseController {

    private static final String DB_URL = "mongodb://localhost:27017";
    private static final String DB_NAME = "bot_db";

    public static String checkUser(User user) {
        return checkUserData(user.getFirstName(), user.getLastName(), user.getId(), user.getUserName());
    }

    public static String saveMessage(Message message) {
        return processMessage(message.getFrom().getId(), message.getChatId(), message.getMessageId(), message.getForwardFrom().getId());
    }

    private static String checkUserData(String first_name, String last_name, long user_id, String username) {
        try (MongoClient mongoClient = new MongoClient(new MongoClientURI(DB_URL))) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection("users");
            long found = collection.countDocuments(Document.parse("{id : " + user_id + "}"));
            if (found == 0) {
                Document doc = new Document("first_name", first_name)
                        .append("last_name", last_name)
                        .append("id", user_id)
                        .append("username", username);
                collection.insertOne(doc);
                mongoClient.close();
                System.out.println("User not exists in database. Written.");
                return "no_exists";
            } else {
                System.out.println("User exists in database.");
                mongoClient.close();
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

}
