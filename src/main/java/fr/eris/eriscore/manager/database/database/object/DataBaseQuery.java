package fr.eris.eriscore.manager.database.database.object;

import fr.eris.eriscore.utils.storage.Tuple;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DataBaseQuery {

    @Getter private final List<Tuple<String, Object>> query;

    protected DataBaseQuery() {
        query = new ArrayList<>();
    }

    public static DataBaseQuery createQuery(String key, Object queryValue) {
        DataBaseQuery newDataBaseQuery = new DataBaseQuery();
        return newDataBaseQuery.appendQuery(key, queryValue);
    }

    public DataBaseQuery appendQuery(String key, Object queryValue) {
        query.add(new Tuple<>(key, queryValue));
        return this;
    }
}
