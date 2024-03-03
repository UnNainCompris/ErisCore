package fr.eris.eriscore.manager.database.database.object;

import fr.eris.eriscore.manager.database.database.DataBase;

import java.util.Date;

public abstract class DataBaseDocument<TDataBase extends DataBase<?>> {

    public TDataBase parentDataBase;
    public abstract DataBaseDocument<TDataBase> set(String key, Object value);

    public abstract <T> T getOrDefault(String key, T defaultValue);

    public abstract Object getRaw(String key);

    public abstract boolean isExist(String key);

    public abstract String getString(String key);
    public abstract Integer getInt(String key);
    public abstract Double getDouble(String key);
    public abstract Float getFloat(String key);
    public abstract Long getLong(String key);
    public abstract Short getShort(String key);
    public abstract Byte getByte(String key);
    public abstract Boolean getBoolean(String key);
    public abstract Date getCreationDate();

}
