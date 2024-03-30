package fr.eris.eriscore.manager.database.database.mongo;

import fr.eris.eriscore.manager.database.database.object.DataBaseDocument;
import fr.eris.eriscore.manager.database.execption.ErisDatabaseException;
import org.bson.Document;

import java.util.Date;

public class MongoDocument extends DataBaseDocument<MongoDataBase> {

    private final Document assignedDocument;

    public MongoDocument(Document assignedDocument, MongoDataBase parentDataBase) {
        this.assignedDocument = assignedDocument;
        this.parentDataBase = parentDataBase;
    }

    public DataBaseDocument<MongoDataBase> set(String key, Object value) {
        Document old = new Document(assignedDocument); // Create a clone for the most accurate filter for the object
        assignedDocument.put(key, value);
        parentDataBase.retrieveCollection().updateOne(old, new Document().append("$set", assignedDocument));
        return this;
    }

    public boolean isExist(String key) {
        return assignedDocument.containsKey(key);
    }

    public Date getCreationDate() {
        return assignedDocument.getObjectId("_id").getDate();
    }

    public <T> T getOrDefault(String key, T defaultValue) {
        if(!assignedDocument.containsKey(key)) return defaultValue;
        Object value = assignedDocument.get(key);
        if(value == null) return defaultValue;
        else if(value.getClass().isAssignableFrom(defaultValue.getClass()) ||
                defaultValue.getClass().isAssignableFrom(value.getClass()))
            return (T) value;
        return null;
    }

    public <T> T getAndCast(String key, Class<T> castingClassType) {
        Object value = assignedDocument.get(key);
        if(value == null) return null;
        if(castingClassType.isAssignableFrom(value.getClass()))
            return castingClassType.cast(value);
        throw new ErisDatabaseException("Illegal class type use ! {requiredClass" + castingClassType +
                ";foundedClass" + value.getClass() + "}");
    }

    public Object getRaw(String key) {
        return assignedDocument.get(key);
    }

    public String getString(String key) {
        return getAndCast(key, String.class);
    }

    public Integer getInt(String key) {
        return getAndCast(key, Integer.class);
    }

    public Double getDouble(String key) {
        return getAndCast(key, Double.class);
    }

    public Float getFloat(String key) {
        return getAndCast(key, Float.class);
    }

    public Long getLong(String key) {
        return getAndCast(key, Long.class);
    }

    public Short getShort(String key) {
        return getAndCast(key, Short.class);
    }

    public Byte getByte(String key) {
        return getAndCast(key, Byte.class);
    }

    public Boolean getBoolean(String key) {
        return getAndCast(key, Boolean.class);
    }
}
