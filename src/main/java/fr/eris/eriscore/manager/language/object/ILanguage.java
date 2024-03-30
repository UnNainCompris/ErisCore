package fr.eris.eriscore.manager.language.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.language.object.Language;
import fr.eris.eriscore.api.manager.language.object.LanguagePath;
import fr.eris.eriscore.utils.data.IData;
import fr.eris.eriscore.utils.file.FileUtils;
import fr.eris.eriscore.utils.reflections.ReflectionUtils;
import fr.eris.yaml.api.Yaml;
import fr.eris.yaml.api.object.YamlDocument;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class ILanguage extends IData implements Language {

    @Getter private final String languageName;
    private YamlDocument languageDocument;

    private final HashMap<String, Field> langPathMap = new HashMap<>();

    public ILanguage(String languageName) {
        this.languageName = languageName;
        this.languageDocument = Yaml.getYaml().createEmptyDocument();
        load();
    }

    private void loadDefault() {
        boolean isFileExist = FileUtils.isExist(getSaveDirectory(), getSaveFileName());
        boolean isDocumentUpdated = false;

        if(!isFileExist) ErisCore.getDebugger().info("Loading default language of " + languageName + " !");

        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                String yamlPath = languageName + "." + languagePath.value();
                if(languageDocument.contains(yamlPath))
                    continue;
                isDocumentUpdated = true;
                String fieldValue = (String) field.get(this);
                languageDocument.set(languageName + "." + languagePath.value(), fieldValue);
                ErisCore.getDebugger().development("Loaded field " + field.getName() + " at " + languagePath.value() + " !");
            } catch (IllegalAccessException exception) {
                ErisCore.getDebugger().severe("Error while trying to default load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        if(isDocumentUpdated) save();
        if(!isFileExist) ErisCore.getDebugger().info("Finish loading default language of " + languageName + " !");
    }

    public void load() {
        if(FileUtils.isExist(getSaveDirectory(), getSaveFileName()))
            languageDocument = Yaml.getYaml().createDocumentFromFile(
                    FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()));
        else languageDocument = Yaml.getYaml().createEmptyDocument();

        loadDefault();
        ErisCore.getDebugger().info("Loading language of " + languageName + " !");
        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                String yamlPath = languageName + "." + languagePath.value();
                langPathMap.put(yamlPath, field);

                field.set(this, languageDocument.getString(yamlPath));
                ErisCore.getDebugger().development("Loaded field " + field.getName() + " at " + languagePath.value() + " !");
            } catch (IllegalAccessException exception) {
                ErisCore.getDebugger().severe("Error while trying to load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        ErisCore.getDebugger().info("Finish loading language of " + languageName + " !");
    }

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()), languageDocument.serialize());
    }

    public String getSaveFileName() {
        return "lang.yml";
    }

    public String getLang(String path) {
        try {
            Field targetField = langPathMap.get(path);
            if(targetField == null) return "";
            targetField.setAccessible(true);
            return (String) targetField.get(this);
        } catch (Exception e) {
            return "";
        }
    }
}
