package fr.eris.eriscore.manager.database.database.object;

import fr.eris.eriscore.manager.database.database.DataBase;

public abstract class DataBaseDocument<TDataBase extends DataBase<?>> {

    public TDataBase parentDataBase;
    public abstract DataBaseDocument<TDataBase> set(String key, Object value);

    public abstract <T> T getOrDefault(String key, T defaultValue);

    public abstract Object getRaw(String key);

}
