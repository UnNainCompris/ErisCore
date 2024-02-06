package fr.eris.erisutils.manager.config.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.eris.erisutils.utils.data.IData;
import fr.eris.erisutils.utils.file.FileUtils;

import java.io.File;

public abstract class IJsonConfig extends IData {

    private static final Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .create();

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()), gson.toJson(this));
    }

    public static <T extends IJsonConfig> T load(String data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public static <T extends IJsonConfig> T load(File dataFile, Class<T> clazz) {
        if(!dataFile.exists() || dataFile.isDirectory()) {
            throw new IllegalArgumentException("Invalid provided file to load a config from it ! " + dataFile.getName());
        }
        return gson.fromJson(FileUtils.readFile(dataFile), clazz);
    }
}
