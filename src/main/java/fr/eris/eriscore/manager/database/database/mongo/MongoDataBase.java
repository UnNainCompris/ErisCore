package fr.eris.eriscore.manager.database.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.eris.eriscore.manager.database.database.DataBase;
import fr.eris.eriscore.manager.database.database.object.DataBaseQuery;
import fr.eris.eriscore.manager.database.execption.ErisDatabaseException;
import fr.eris.eriscore.utils.storage.Tuple;
import org.bson.Document;

import java.util.*;


public class MongoDataBase extends DataBase<MongoDocument> {

    private final String defaultCollectionName = "ErisCore";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDataBase(String targetDataBase, String targetHost,
                         String targetPort, String password, String username) {
        super(targetDataBase, targetHost, targetPort, password, username);
    }

    protected boolean connect() {
        try {
            MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder();
            settingsBuilder.applyConnectionString(new ConnectionString(buildConnectionUrl(false)));
            settingsBuilder.credential(MongoCredential.createCredential(username, targetDataBase, password.toCharArray()));
            mongoClient = MongoClients.create(settingsBuilder.build());

            database = mongoClient.getDatabase(targetDataBase);
            collection = database.getCollection("ErisCore");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void changeCollection(String newCollectionName) {
        this.collection = database.getCollection(newCollectionName == null ? defaultCollectionName : newCollectionName);
    }

    public void setToDefaultCollection() {
        changeCollection(null);
    }

    public String buildConnectionUrl(boolean hideSensitiveData) {
        if(hideSensitiveData)
            return "mongodb://" + username + ":<pwd:hidden>@<host:hidden>:<port:hidden>/" + targetDataBase;
        else return "mongodb://" + username + ":" + password + "@" + targetHost + ":" + targetPort + "/" + targetDataBase;
    }

    public void insertIfAbsent(DataBaseQuery dataBaseQuery) {
        validateCollection();
        Document searchQuery = buildDocumentFromQuery(dataBaseQuery);
        if(!find(searchQuery).isEmpty()) return;
        collection.insertOne(searchQuery);
    }

    public void validateCollection() {
        if(collection == null) {
            throw new ErisDatabaseException("Try accessing an collection whilst the requested collection is null !");
        }
    }

    public MongoCollection<Document> retrieveCollection() {
        return collection;
    }

    public Set<MongoDocument> find(DataBaseQuery dataBaseQuery) {
        validateCollection();
        Document searchQuery = buildDocumentFromQuery(dataBaseQuery);
        return find(searchQuery);
    }
    public Set<MongoDocument> find(Document searchQuery) {
        Set<MongoDocument> documents = new HashSet<>();
        for(Document document : retrieveCollection().find(searchQuery))
            documents.add(new MongoDocument(document));
        return documents;
    }


    public MongoDocument findFirst(DataBaseQuery dataBaseQuery) {
        validateCollection();
        Document searchQuery = buildDocumentFromQuery(dataBaseQuery);
        return new MongoDocument(retrieveCollection().find(searchQuery).first());
    }

    public void delete(DataBaseQuery dataBaseQuery) {
        validateCollection();
        Document searchQuery = buildDocumentFromQuery(dataBaseQuery);
        collection.deleteMany(searchQuery);
    }

    public Document buildDocumentFromQuery(DataBaseQuery dataBaseQuery) {
        Document document = new Document();
        for(Tuple<String, Object> query : dataBaseQuery.getQuery())
            document.put(query.getA(), query.getB());
        return document;
    }
}
