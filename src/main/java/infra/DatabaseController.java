package infra;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;

public class DatabaseController {

    public static String checkUser(User user) {
        return checkUserData(user.getFirstName(), user.getLastName(), user.getId(), user.getUserName());
    }

    public static String saveMessage(Message message) {
        return processMessage(message.getFrom().getId(), message.getChatId(), message.getMessageId(), message.getForwardFrom().getId());
    }

    private static String checkUserData(String first_name, String last_name, long user_id, String username) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("bot_db");
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
    }

    private static String processMessage(long userId, long chatId, long messageId, long forwardedFromId) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("bot_db");
        MongoCollection<Document> collection = database.getCollection("messages");
//        long found = collection.countDocuments(Document.parse("{id : " + user_id + "}"));
//        if (found == 0) {
        Document doc = new Document("user_id", userId)
                .append("chat_id", chatId)
                .append("message_id", messageId)
                .append("forwarded_from_id", forwardedFromId)
                .append("created", new Date());
        System.out.println(doc.get("_id"));
        collection.insertOne(doc);
        System.out.println(doc.get("_id"));
        mongoClient.close();
        System.out.println("User not exists in database. Written.");
        return "no_exists";
//        } else {
//            System.out.println("User exists in database.");
//            mongoClient.close();
//            return "exists";
//        }
    }

}
