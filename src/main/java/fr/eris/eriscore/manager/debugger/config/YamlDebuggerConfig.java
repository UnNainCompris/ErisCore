package fr.eris.eriscore.manager.debugger.config;

import fr.eris.eriscore.manager.config.object.yaml.IYamlConfig;
import fr.eris.eriscore.manager.debugger.object.DebugType;
import fr.eris.eriscore.utils.file.FileCache;

import java.io.File;

public class YamlDebuggerConfig extends IYamlConfig {

    private static YamlDebuggerConfig debuggerConfig;

    public void loadDefaultConfig(String debuggerName) {
        set(debuggerName.toLowerCase() + ".enabled", false);
        set(debuggerName.toLowerCase() + ".prefix", "&6[&7" + debuggerName + " Debugger&6]");
        set(debuggerName.toLowerCase() + ".format", "%prefix% - %type% &e- &9%message%");
        for(DebugType type : DebugType.values()) {
            set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".useCustomFormat", false);
            set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".customFormat", "%prefix% - %type% &e- &9%message%");
            set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".typeDisplay", type.getDisplay());
            set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".enabled", true);
        }
    }

    public void loadDefaultConfig() {

    }

    public boolean isDebuggerExist(String debuggerName) {
        return isBoolean(debuggerName + ".enabled");
    }

    public static YamlDebuggerConfig getConfig() {
        if(debuggerConfig == null) debuggerConfig = new YamlDebuggerConfig();
        return debuggerConfig;
    }

    public File getSaveDirectory() {
        return FileCache.PLUGIN_DIR;
    }

    public String getSaveFileName() {
        return "debug.yml";
    }
}
