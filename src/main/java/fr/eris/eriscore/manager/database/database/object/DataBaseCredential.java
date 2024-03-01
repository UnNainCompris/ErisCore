package fr.eris.eriscore.manager.database.database.object;

import lombok.Getter;

public class DataBaseCredential {
    @Getter
    private final String targetDataBase, targetHost, targetPort, password, username;

    public DataBaseCredential(String targetDataBase, String targetHost,
                              String targetPort, String password, String username) {
        this.targetDataBase = targetDataBase;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.password = password;
        this.username = username;
    }

    public static DataBaseCredential build(String targetDataBase, String targetHost,
                                           String targetPort, String password, String username) {
        return new DataBaseCredential(targetDataBase, targetHost, targetPort, password, username);
    }
}
