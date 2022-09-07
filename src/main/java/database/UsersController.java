package database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.User;

public class UsersController {

    private UsersController(){

    }
    public static int insert(User user){
        MongoDatabase connection = CloudMongoDBService.connect();
        MongoCollection<Document> documentMongoCollection = CloudMongoDBService.users(connection);
        Document doc = new Document("first_name", user.getFirstName())
                .append("last_name", user.getLastName())
                .append("id", user.getId())
                .append("username", user.getUserName());
        documentMongoCollection.insertOne(doc);
        return 200;
    }
}
