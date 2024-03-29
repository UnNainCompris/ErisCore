package fr.eris.eriscore.manager.debugger.config;

import fr.eris.eriscore.manager.config.object.yaml.IYamlConfig;
import fr.eris.eriscore.manager.debugger.object.DebugType;
import fr.eris.eriscore.utils.data.IData;
import fr.eris.eriscore.utils.file.FileCache;
import fr.eris.eriscore.utils.file.FileUtils;
import fr.eris.yaml.api.Yaml;
import fr.eris.yaml.api.object.YamlDocument;
import lombok.Getter;

import java.io.File;

public class YamlDebuggerConfig extends IData {

    private static YamlDebuggerConfig debuggerConfig;

    @Getter private YamlDocument dataDocument;

    public YamlDebuggerConfig() {
        if(FileUtils.isExist(getSaveDirectory(), getSaveFileName()))
            dataDocument = Yaml.getYaml().createDocumentFromFile(
                    FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()));
        else dataDocument = Yaml.getYaml().createEmptyDocument();
    }

    public void loadDefaultConfig(String debuggerName) {
        dataDocument.set(debuggerName.toLowerCase() + ".enabled", false);
        dataDocument.set(debuggerName.toLowerCase() + ".prefix", "&6[&7" + debuggerName + " Debugger&6]");
        dataDocument.set(debuggerName.toLowerCase() + ".format", "%prefix% - %type% &e- &9%message%");
        for(DebugType type : DebugType.values()) {
            dataDocument.set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".useCustomFormat", false);
            dataDocument.set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".customFormat", "%prefix% - %type% &e- &9%message%");
            dataDocument.set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".typeDisplay", type.getDisplay());
            dataDocument.set(debuggerName.toLowerCase() + "." + type.name().toLowerCase() + ".enabled", true);
        }
    }

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()), dataDocument.serialize());
    }

    public boolean isDebuggerExist(String debuggerName) {
        return dataDocument.contains(debuggerName + ".enabled") &&
                dataDocument.getBoolean(debuggerName + ".enabled");
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
