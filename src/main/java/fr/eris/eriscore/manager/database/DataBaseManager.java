package fr.eris.eriscore.manager.database;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.database.database.DataBase;
import fr.eris.eriscore.utils.manager.Manager;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager extends Manager<ErisCore> {
    // every static method to prevent the access of other manager to create error !
    private static final List<DataBase<?>> loadedDatabase = new ArrayList<>();

    public void start() {
    }

    public void stop() {
        loadedDatabase.forEach((dataBase) -> dataBase.requestDisconnect(true));
    }

    public static boolean isOtherSimilarDatabase(DataBase<?> dataBase) {
        for(DataBase<?> currentDataBase : loadedDatabase)
            if(currentDataBase.isSimilar(dataBase)) return true;
        return false;
    }

    public static void appendNewDatabase(DataBase<?> newDataBase) {
        loadedDatabase.add(newDataBase);
    }
}
