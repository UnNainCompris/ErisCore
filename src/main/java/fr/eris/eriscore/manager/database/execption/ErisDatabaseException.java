package fr.eris.eriscore.manager.database.execption;

public class ErisDatabaseException extends RuntimeException {
    public ErisDatabaseException(String message) {
        super(message);
    }

    public ErisDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErisDatabaseException(Throwable cause) {
        super(cause);
    }

    public ErisDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
