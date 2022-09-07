package database;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import infra.Configuration;
import org.bson.Document;

public class CloudMongoDBService {

    private CloudMongoDBService(){

    }

    private static final String DB_URL = Configuration.getConfig().databaseUrl();
    private static final String DB_NAME = Configuration.getConfig().databaseName();
    public static MongoDatabase connect(){

        ConnectionString connectionString = new ConnectionString(DB_URL);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            return mongoClient.getDatabase(DB_NAME);
        } catch (MongoClientException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static MongoCollection<Document> users(MongoDatabase database) {
        return database.getCollection("users");
    }
}
