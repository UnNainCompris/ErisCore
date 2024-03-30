package fr.eris.eriscore.api.manager.debugger.object;

public class DebugTypeConfig {
    public final DebugType type;
    public final boolean isEnabled;
    public final String typeDisplay;
    public final String format;

    public DebugTypeConfig(DebugType type, boolean isEnabled, String typeDisplay, String format) {
        this.type = type;
        this.isEnabled = isEnabled;
        this.typeDisplay = typeDisplay;
        this.format = format;
    }
}
