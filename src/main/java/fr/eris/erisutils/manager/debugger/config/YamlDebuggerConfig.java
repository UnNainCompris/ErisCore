package fr.eris.erisutils.manager.debugger.config;

import fr.eris.erisutils.manager.config.object.yaml.IYamlConfig;
import fr.eris.erisutils.utils.file.FileCache;

import java.io.File;

public class YamlDebuggerConfig extends IYamlConfig {

    private static YamlDebuggerConfig debuggerConfig;

    public void loadDefaultConfig(String debuggerName) {
        set(debuggerName + ".enabled", false);
        set(debuggerName + ".prefix", "&6[&7ErisCore Debugger&6]");
        set(debuggerName + ".format", "%prefix% &e- &9%message%");
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
