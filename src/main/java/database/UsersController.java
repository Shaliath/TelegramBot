package database;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.telegram.telegrambots.meta.api.objects.User;

public class UsersController {

    private UsersController(){

    }
    public static int create(User user){
        try (MongoClient mongoClient = MongoClients.create(CloudMongoDBService.getSettings())) {
            MongoDatabase db = mongoClient.getDatabase(CloudMongoDBService.DB_NAME);
            MongoCollection<Document> documentMongoCollection = CloudMongoDBService.users(db);
            Document doc = new Document("first_name", user.getFirstName())
                    .append("last_name", user.getLastName())
                    .append("user_id", user.getId())
                    .append("username", user.getUserName());
            documentMongoCollection.insertOne(doc);
            return 200;
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
            return 500;
        }
    }

    public static Document getByUserId(Long userId){
        try (MongoClient mongoClient = MongoClients.create(CloudMongoDBService.getSettings())) {
            MongoDatabase db = mongoClient.getDatabase(CloudMongoDBService.DB_NAME);
            MongoCollection<Document> documentMongoCollection = CloudMongoDBService.users(db);
            Bson filter = Filters.and(Filters.eq("user_id", userId));
            return documentMongoCollection.find(filter).first();
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
            return new Document();
        }
    }

    public static UpdateResult updateOne(User user){
        try (MongoClient mongoClient = MongoClients.create(CloudMongoDBService.getSettings())) {
            MongoDatabase db = mongoClient.getDatabase(CloudMongoDBService.DB_NAME);
            MongoCollection<Document> documentMongoCollection = CloudMongoDBService.users(db);
            return documentMongoCollection.updateOne(Filters.eq("user_id", user.getId()), new Document("first_name", user.getFirstName())
                    .append("last_name", user.getLastName())
                    .append("user_id", user.getId())
                    .append("username", user.getUserName()));
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
