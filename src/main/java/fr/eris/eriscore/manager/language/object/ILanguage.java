package fr.eris.eriscore.manager.language.object;

import fr.eris.eriscore.manager.config.object.yaml.IYamlConfig;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.reflections.ReflectionUtils;

import java.lang.reflect.Field;

public abstract class ILanguage extends IYamlConfig {

    private final String languageName;

    public ILanguage(String languageName) {
        this.languageName = languageName;
        if(!isSection(languageName)) loadDefaultConfig();
    }

    public void loadDefaultConfig() {
        Debugger.getDebugger("ErisCore").info("Loading default language of " + languageName + " !");
        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                String fieldValue = (String) field.get(this);
                set(languageName + "." + languagePath.path(), fieldValue);
                Debugger.getDebugger("ErisCore").info("Loaded field " + field.getName() + " at " + languagePath.path() + " !");
            } catch (IllegalAccessException exception) {
                Debugger.getDebugger("ErisCore").severe("Error while trying to default load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        save();
        Debugger.getDebugger("ErisCore").info("Finish loading default language of " + languageName + " !");
    }

    public void load() {
        super.load();
        Debugger.getDebugger("ErisCore").info("Loading language of " + languageName + " !");
        for(Field field : ReflectionUtils.retrieveAnnotatedFieldOfType(this.getClass(), String.class, LanguagePath.class)) {
            try {
                field.setAccessible(true);
                LanguagePath languagePath = field.getAnnotation(LanguagePath.class);
                field.set(this, getString(languageName + "." + languagePath.path()));
                Debugger.getDebugger("ErisCore").info("Loaded field " + field.getName() + " at " + languagePath.path() + " !");
            } catch (IllegalAccessException exception) {
                Debugger.getDebugger("ErisCore").severe("Error while trying to load a language field ! " +
                        "{" + field.getName() + ", " + exception.getClass().getName() + "}");
            }
        }
        Debugger.getDebugger("ErisCore").info("Finish loading language of " + languageName + " !");
    }

    public String getSaveFileName() {
        return "lang.yml";
    }
}
