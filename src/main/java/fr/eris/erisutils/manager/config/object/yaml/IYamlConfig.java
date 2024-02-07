package fr.eris.erisutils.manager.config.object.yaml;

import fr.eris.erisutils.utils.data.IData;
import fr.eris.erisutils.utils.file.FileUtils;
import org.bukkit.Color;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public abstract class IYamlConfig extends IData {
    private YamlConfiguration yaml;

    public IYamlConfig() {
        yaml = new YamlConfiguration();
        if(!FileUtils.isExist(getSaveDirectory(), getSaveFileName())) {
            loadDefaultConfig();
        } else {
            load();
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

    public void set(String path, Object value) {
        yaml.set(path, value);
    }

    public boolean getBoolean(String path, boolean def) {
        return yaml.getBoolean(path, def);
    }

    public boolean getBoolean(String path) {
        return yaml.getBoolean(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return yaml.getBooleanList(path);
    }

    public boolean isBoolean(String path) {
        return yaml.isBoolean(path);
    }

    public String getString(String path, String def) {
        return yaml.getString(path, def);
    }

    public String getString(String path) {
        return yaml.getString(path);
    }

    public List<String> getStringList(String path) {
        return yaml.getStringList(path);
    }

    public boolean isString(String path) {
        return yaml.isString(path);
    }

    public int getInt(String path, int def) {
        return yaml.getInt(path, def);
    }

    public int getInt(String path) {
        return yaml.getInt(path);
    }

    public List<Integer> getIntegerList(String path) {
        return yaml.getIntegerList(path);
    }

    public boolean isInt(String path) {
        return yaml.isInt(path);
    }

    public long getLong(String path, long def) {
        return yaml.getLong(path, def);
    }

    public long getLong(String path) {
        return yaml.getLong(path);
    }

    public List<Long> getLongList(String path) {
        return yaml.getLongList(path);
    }

    public boolean isLong(String path) {
        return yaml.isLong(path);
    }

    public Color getColor(String path, Color def) {
        return yaml.getColor(path, def);
    }

    public Color getColor(String path) {
        return yaml.getColor(path);
    }

    public boolean isColor(String path) {
        return yaml.isColor(path);
    }

    public double getDouble(String path) {
        return yaml.getDouble(path);
    }

    public List<Double> getDoubleList(String path) {
        return yaml.getDoubleList(path);
    }

    public boolean isDouble(String path) {
        return yaml.isDouble(path);
    }
}
