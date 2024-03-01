package fr.eris.eriscore.manager.database.database;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.database.DataBaseManager;
import fr.eris.eriscore.manager.database.database.object.DataBaseQuery;
import fr.eris.eriscore.manager.database.event.OnDataBaseConnect;
import fr.eris.eriscore.manager.database.event.OnDataBaseDisconnect;
import fr.eris.eriscore.manager.database.execption.ErisDatabaseException;
import fr.eris.eriscore.manager.database.database.object.DataBaseDocument;
import lombok.Getter;

import java.util.Set;

public abstract class DataBase<TDocument extends DataBaseDocument<?>> {

    protected final String targetDataBase, targetHost, targetPort, password, username;

    @Getter private boolean connected;

    public DataBase(String targetDataBase, String targetHost,
                    String targetPort, String password, String username) {
        this.targetDataBase = targetDataBase;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.password = password;
        this.username = username;

        if(DataBaseManager.isOtherSimilarDatabase(this)) {
            throw new ErisDatabaseException("Cannot have 2 similar (same user:" + username + ", " +
                    "targetDatabase:" + targetDataBase + ", targetPort:<hidden>, targetHost:<hidden>) " +
                    "database loaded at the same time !");
        }
        DataBaseManager.appendNewDatabase(this);
        requestConnect(false);
    }

    public void requestConnect(boolean silent) {
        if(!silent) {
            OnDataBaseConnect onDataBaseConnect = new OnDataBaseConnect(this);
            ErisCore.postEvent(onDataBaseConnect);
            if (onDataBaseConnect.isCancelled()) return;
        }
        connected = connect();
    }

    public void requestDisconnect(boolean silent) {
        if(!connected) {
            System.out.println("Not connected " + targetDataBase);
        }
        if(!silent) {
            OnDataBaseDisconnect onDataBaseDisconnect = new OnDataBaseDisconnect(this);
            ErisCore.postEvent(onDataBaseDisconnect);
            if (onDataBaseDisconnect.isCancelled()) return;
        }
        disconnect();
        connected = false;
    }

    /**
     * @return true if the plugin establish a connection with the database
     */
    protected abstract boolean connect();
    protected void disconnect() {}

    /**
     * Used to build the connection url to connect to the database
     * @param hideSensitiveData Used to know if we should hide sensitive data in the returned string (Use for printing error)
     * @return the connection url
     */
    public abstract String buildConnectionUrl(boolean hideSensitiveData);

    public abstract void insertIfAbsent(DataBaseQuery dataBaseQuery);

    /**
     * @param dataBaseQuery the query used to find element in the current collection
     * @return return every element find in the current collection using the query
     */
    public abstract Set<TDocument> find(DataBaseQuery dataBaseQuery);

    /**
     * @param dataBaseQuery The query used to search in the current collection
     * @return The first element find in the current collection using the query
     */
    public abstract TDocument findFirst(DataBaseQuery dataBaseQuery);

    /**
     * Use to delete every object in the database that matches with the query
     * @param dataBaseQuery the query use to fin matching element
     */
    public abstract void delete(DataBaseQuery dataBaseQuery);

    /**
     * Used to check if 2 database instance are similar between each-other
     * @param otherDataBase Another database to check
     * @return tru if both of the database are similar
     */
    public boolean isSimilar(DataBase<?> otherDataBase) {
        return otherDataBase.targetDataBase.equalsIgnoreCase(this.targetDataBase) &&
                otherDataBase.targetHost.equalsIgnoreCase(this.targetHost) &&
                otherDataBase.targetPort.equalsIgnoreCase(this.targetPort) &&
                otherDataBase.username.equalsIgnoreCase(this.username);
    }
}
