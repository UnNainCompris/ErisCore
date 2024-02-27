package fr.eris.eriscore.manager.database.database.mongo;

import fr.eris.eriscore.manager.database.database.object.DataBaseDocument;
import org.bson.Document;

public class MongoDocument extends DataBaseDocument<MongoDataBase> {

    private final Document assignedDocument;

    public MongoDocument(Document assignedDocument) {
        this.assignedDocument = assignedDocument;
    }

    public DataBaseDocument<MongoDataBase> set(String key, Object value) {
        Document old = new Document(assignedDocument); // Create a clone for the most accurate filter for the object
        assignedDocument.put(key, value);
        parentDataBase.retrieveCollection().updateOne(old, new Document().append("$set", assignedDocument));
        return this;
    }

    public <T> T getOrDefault(String key, T defaultValue) {
        Object value = assignedDocument.get(key);
        if(value == null) return defaultValue;
        else if(value.getClass().isAssignableFrom(defaultValue.getClass()) ||
                defaultValue.getClass().isAssignableFrom(value.getClass()))
            return (T) value;
        return null;
    }

    public Object getRaw(String key) {
        return assignedDocument.get(key);
    }
}
