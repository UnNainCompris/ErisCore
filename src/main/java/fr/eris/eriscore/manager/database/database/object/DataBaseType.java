package fr.eris.eriscore.manager.database.database.object;

import fr.eris.eriscore.manager.database.database.DataBase;
import fr.eris.eriscore.manager.database.database.mongo.MongoDataBase;
import lombok.Getter;

public enum DataBaseType {
    MYSQL(null), MONGO(MongoDataBase.class);

    @Getter
    private final Class<? extends DataBase<?>> assignedDatabaseClass;

    DataBaseType(Class<? extends DataBase<?>> assignedDatabaseClass) {
        this.assignedDatabaseClass = assignedDatabaseClass;
    }
}
