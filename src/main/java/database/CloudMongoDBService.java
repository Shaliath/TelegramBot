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
    public static final String DB_NAME = Configuration.getConfig().databaseName();
    private static final String DB_URL = Configuration.getConfig().databaseUrl();

    public static MongoClientSettings getSettings(){
        ConnectionString connectionString = new ConnectionString(DB_URL);
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
    }

    public static MongoCollection<Document> users(MongoDatabase database) {
        return database.getCollection("users");
    }
}
