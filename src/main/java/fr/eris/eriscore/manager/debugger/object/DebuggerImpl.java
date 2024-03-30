package fr.eris.eriscore.manager.debugger.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.debugger.object.DebugType;
import fr.eris.eriscore.api.manager.debugger.object.DebugTypeConfig;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.debugger.config.YamlDebuggerConfig;
import fr.eris.eriscore.utils.bukkit.color.ColorUtils;
import lombok.Getter;

import java.util.HashMap;

public class DebuggerImpl implements Debugger {

    private HashMap<DebugType, DebugTypeConfig> configMap = new HashMap<>();
    private final String debuggerName, debuggerNameLowerCase;

    @Getter private boolean enabled;
    private String prefix, format;

    public DebuggerImpl(String debuggerName) {
        this.debuggerName = debuggerName;
        this.debuggerNameLowerCase = debuggerName.toLowerCase();
        setup();
    }

    private void setup() {
        YamlDebuggerConfig config = YamlDebuggerConfig.getConfig();
        if(!config.isDebuggerExist(debuggerNameLowerCase))
            config.loadDefaultConfig(debuggerName);

        enabled = config.getDataDocument().getBoolean(debuggerNameLowerCase + ".enabled", false);
        prefix = config.getDataDocument().getString(debuggerNameLowerCase + ".prefix", "&6[&7" + debuggerName + " Debugger&6]");
        format = config.getDataDocument().getString(debuggerNameLowerCase + ".format", "%prefix% - %type% &e- &9%message%");
        for(DebugType type : DebugType.values()) {
            String currentFormat =
                    config.getDataDocument().getBoolean(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".useCustomFormat", false) ?
                            config.getDataDocument().getString(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".customFormat",
                            "%prefix% - %type% &e- &9%message%") : format;
            configMap.put(type, new DebugTypeConfig(type,
                    config.getDataDocument().getBoolean(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".enabled", true),
                    config.getDataDocument().getString(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".typeDisplay", type.getDisplay()),
                    currentFormat));
        }
    }

    public void debug(String message, DebugType type) {
        DebugTypeConfig config = configMap.get(type);
        if(!enabled || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void info(String message) {
        debug(message, DebugType.INFO);
    }

    public void warning(String message) {
        debug(message, DebugType.WARNING);
    }

    public void error(String message) {
        debug(message, DebugType.ERROR);
    }

    public void severe(String message) {
        debug(message, DebugType.SEVERE);
    }

    public void test(String message) {
        debug(message, DebugType.TEST);
    }
    public void development(String message) {
        debug(message, DebugType.DEVELOPMENT);
    }
}
