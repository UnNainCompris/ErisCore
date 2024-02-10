package fr.eris.eriscore.manager.debugger.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.config.YamlDebuggerConfig;
import fr.eris.eriscore.utils.ColorUtils;
import lombok.Getter;
import org.bukkit.plugin.PluginLogger;

import java.util.HashMap;

public class Debugger {

    private HashMap<DebugType, DebugTypeConfig> configMap = new HashMap<>();
    private final String debuggerName, debuggerNameLowerCase;

    @Getter private boolean isEnable;
    private String prefix, format;

    public Debugger(String debuggerName) {
        this.debuggerName = debuggerName;
        this.debuggerNameLowerCase = debuggerName.toLowerCase();
        setup();
    }

    private void setup() {
        YamlDebuggerConfig config = YamlDebuggerConfig.getConfig();
        if(!config.isDebuggerExist(debuggerNameLowerCase))
            config.loadDefaultConfig(debuggerName);

        isEnable = config.getBoolean(debuggerNameLowerCase + ".enabled", false);
        prefix = config.getString(debuggerNameLowerCase + ".prefix", "&6[&7" + debuggerName + " Debugger&6]");
        format = config.getString(debuggerNameLowerCase + ".format", "%prefix% - %type% &e- &9%message%");
        for(DebugType type : DebugType.values()) {
            String currentFormat =
                    config.getBoolean(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".useCustomFormat", false) ?
                    config.getString(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".customFormat",
                            "%prefix% - %type% &e- &9%message%") : format;
            configMap.put(type, new DebugTypeConfig(type,
                    config.getBoolean(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".enabled", true),
                    config.getString(debuggerNameLowerCase + "." + type.name().toLowerCase() + ".typeDisplay", type.getDisplay()),
                    currentFormat));
        }
    }

    public void debug(String message, DebugType type) {
        DebugTypeConfig config = configMap.get(type);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void info(String message) {
        DebugTypeConfig config = configMap.get(DebugType.INFO);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void warning(String message) {
        DebugTypeConfig config = configMap.get(DebugType.WARNING);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void error(String message) {
        DebugTypeConfig config = configMap.get(DebugType.ERROR);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void severe(String message) {
        DebugTypeConfig config = configMap.get(DebugType.SEVERE);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public void test(String message) {
        DebugTypeConfig config = configMap.get(DebugType.TEST);
        if(!isEnable || !config.isEnabled) return;
        ErisCore.getInstance().getServer().getConsoleSender().sendMessage(
                ColorUtils.translateColor(
                        config.format.replace("%prefix%", prefix)
                                .replace("%message%", message)
                                .replace("%type%", config.typeDisplay)));
    }

    public static Debugger getDebugger(String debuggerName) {
        return ErisCore.getDebuggerManager().getDebugger(debuggerName);
    }

    public class DebugTypeConfig {
        private final DebugType type;
        private final boolean isEnabled;
        private final String typeDisplay;
        private final String format;

        public DebugTypeConfig(DebugType type, boolean isEnabled, String typeDisplay, String format) {
            this.type = type;
            this.isEnabled = isEnabled;
            this.typeDisplay = typeDisplay;
            this.format = format;
        }
    }
}
