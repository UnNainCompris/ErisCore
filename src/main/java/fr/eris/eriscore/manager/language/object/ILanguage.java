package fr.eris.eriscore.manager.language.object;

import fr.eris.eriscore.manager.config.object.yaml.IYamlConfig;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.data.IData;
import fr.eris.eriscore.utils.file.FileUtils;
import fr.eris.eriscore.utils.reflections.ReflectionUtils;
import fr.eris.yaml.api.Yaml;
import fr.eris.yaml.api.object.YamlDocument;

import java.lang.reflect.Field;

public abstract class ILanguage extends IData {

    private final String languageName;
    private YamlDocument languageDocument;

    public ILanguage(String languageName) {
        this.languageName = languageName;
        this.languageDocument = Yaml.getYaml().createEmptyDocument();
        load();
    }

    private void loadDefault() {
        boolean isFileExist = FileUtils.isExist(getSaveDirectory(), getSaveFileName());
        boolean isDocumentUpdated = false;

        if(!isFileExist) Debugger.getDebugger().info("Loading default language of " + languageName + " !");

        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                String yamlPath = languageName + "." + languagePath.path();
                if(languageDocument.contains(yamlPath))
                    continue;
                isDocumentUpdated = true;
                String fieldValue = (String) field.get(this);
                languageDocument.set(languageName + "." + languagePath.path(), fieldValue);
                Debugger.getDebugger().development("Loaded field " + field.getName() + " at " + languagePath.path() + " !");
            } catch (IllegalAccessException exception) {
                Debugger.getDebugger().severe("Error while trying to default load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        if(isDocumentUpdated) save();
        if(!isFileExist) Debugger.getDebugger().info("Finish loading default language of " + languageName + " !");
    }

    public void load() {
        if(FileUtils.isExist(getSaveDirectory(), getSaveFileName()))
            languageDocument = Yaml.getYaml().createDocumentFromFile(
                    FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()));
        else languageDocument = Yaml.getYaml().createEmptyDocument();

        loadDefault();
        Debugger.getDebugger().info("Loading language of " + languageName + " !");
        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                field.set(this, languageDocument.getString(languageName + "." + languagePath.path()));
                Debugger.getDebugger().development("Loaded field " + field.getName() + " at " + languagePath.path() + " !");
            } catch (IllegalAccessException exception) {
                Debugger.getDebugger().severe("Error while trying to load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        Debugger.getDebugger().info("Finish loading language of " + languageName + " !");
    }

    public void saveData() {
        FileUtils.writeFile(FileUtils.getOrCreateFile(getSaveDirectory(), getSaveFileName()), languageDocument.serialize());
    }

    public String getSaveFileName() {
        return "lang.yml";
    }
}
