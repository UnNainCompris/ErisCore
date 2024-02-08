package fr.eris.eriscore.manager.config.object.yaml;

import lombok.Getter;

import java.io.File;

@Getter
public class VolatileYamlConfig extends IYamlConfig {

    private final String saveFileName;
    private final File saveDirectory;

    public VolatileYamlConfig(String saveFileName, File saveDirectory) {
        this.saveFileName = saveFileName;
        this.saveDirectory = saveDirectory;
    }

    public void loadDefaultConfig() {

    }
}
