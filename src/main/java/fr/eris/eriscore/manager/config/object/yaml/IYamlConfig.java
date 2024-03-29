package fr.eris.eriscore.manager.config.object.yaml;

import fr.eris.eriscore.utils.data.IData;
import fr.eris.eriscore.utils.file.FileUtils;
import fr.eris.yaml.api.Yaml;

public abstract class IYamlConfig extends IData {
    public IYamlConfig() {
        if (!FileUtils.isExist(getSaveDirectory(), getSaveFileName()))
            saveData();
        else load();
    }

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()),
                Yaml.getYaml().serializeObject(this));
    }

    public void load() {
        Yaml.getYaml().loadObjectFromFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()), this);
    }
}