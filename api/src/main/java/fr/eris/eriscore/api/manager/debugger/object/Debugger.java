package fr.eris.eriscore.api.manager.debugger.object;

public interface Debugger {

    boolean isEnabled();

    void debug(String message, DebugType type);
    void info(String message);
    void warning(String message);
    void error(String message);
    void severe(String message);
    void test(String message);
    void development(String message);

}
