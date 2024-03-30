package fr.eris.eriscore.manager.database;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.database.database.DataBase;
import fr.eris.eriscore.manager.database.database.object.DataBaseCredential;
import fr.eris.eriscore.manager.database.database.object.DataBaseType;
import fr.eris.eriscore.manager.database.execption.ErisDatabaseException;
import fr.eris.eriscore.api.manager.utils.Manager;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager implements Manager<ErisCore> {
    // every static method to prevent the access of other manager to create error !
    private static final List<DataBase<?>> loadedDatabase = new ArrayList<>();
    @Getter @Setter private ErisCore parent;

    public void start() {
    }

    public void stop() {
        loadedDatabase.forEach((dataBase) -> dataBase.requestDisconnect(true));
    }

    public static boolean isOtherSimilarDatabase(DataBaseCredential dataBaseCredential) {
        for(DataBase<?> currentDataBase : loadedDatabase)
            if(currentDataBase.isSimilar(dataBaseCredential)) return true;
        return false;
    }

    public static boolean isOtherSimilarDatabase(DataBase<?> dataBase) {
        for(DataBase<?> currentDataBase : loadedDatabase)
            if(currentDataBase.isSimilar(dataBase)) return true;
        return false;
    }

    public static void appendNewDatabase(DataBase<?> newDataBase) {
        loadedDatabase.add(newDataBase);
    }

    public void loadIfAbsentDatabase(DataBaseType type, DataBaseCredential credential) {
        if(isOtherSimilarDatabase(credential)) return;
        try {
            Constructor<? extends DataBase<?>> constructor =
                    type.getAssignedDatabaseClass().getDeclaredConstructor(DataBaseCredential.class);
            constructor.newInstance(credential);
            // No need to append here because its already append in the database constructor
        } catch (Exception exception) {
            throw new ErisDatabaseException(exception.toString());
        }
    }

    public DataBase<?> retrieveDataBase(String databaseConnectedUsername, String databaseName) {
        return retrieveDataBase(databaseConnectedUsername, databaseName, DataBase.class);
    }

    public <T extends DataBase<?>> T retrieveDataBase(String databaseConnectedUsername,
                                                      String databaseName, Class<T> databaseClass) {
        for(DataBase<?> currentDataBase : loadedDatabase)
            if(currentDataBase.getCredential().getUsername().equals(databaseConnectedUsername) &&
                    currentDataBase.getCredential().getTargetDataBase().equals(databaseName)) {
                if(!databaseClass.isAssignableFrom(currentDataBase.getClass())) continue;
                return databaseClass.cast(currentDataBase);
            }
        return null;
    }

}
