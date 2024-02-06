package fr.eris.erisutils.manager.config.object.yaml;

import fr.eris.erisutils.utils.data.IData;
import fr.eris.erisutils.utils.file.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class IYamlConfig extends IData {
    private YamlConfiguration yaml;

    public IYamlConfig() {
        if(!FileUtils.isExist(getSaveDirectory(), getSaveFileName())) {
            loadDefaultConfig();
        }
    }

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()),
                yaml != null ? yaml.saveToString() : "");
    }

    public void load() {
        yaml = YamlConfiguration.loadConfiguration(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()));
    }

    public abstract void loadDefaultConfig();
}
