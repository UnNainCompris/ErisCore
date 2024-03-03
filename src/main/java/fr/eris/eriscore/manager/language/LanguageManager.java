package fr.eris.eriscore.manager.language;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.language.object.ILanguage;
import fr.eris.eriscore.utils.manager.Manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class LanguageManager extends Manager<ErisCore> {

    private final HashMap<String, ILanguage> loadedLanguage = new HashMap<>();

    public void start() {

    }

    public void stop() {

    }

    public <T extends ILanguage> T getLanguage(String languageName, Class<T> languageType) {
        languageName = languageName.toLowerCase();

        if(!loadedLanguage.containsKey(languageName)) {
            T newLanguage = loadLanguage(languageName, languageType);
            loadedLanguage.put(languageName, newLanguage);
            Debugger.getDebugger().info("Language " + languageName + "is now loaded !");
        } else {
            if(!languageType.isAssignableFrom(loadedLanguage.get(languageName).getClass())) {
                Debugger.getDebugger().error("A language with the same name but not the same type is already loaded");
                throw new IllegalArgumentException();
            }
        }
        return languageType.cast(loadedLanguage.get(languageName));
    }

    private <T extends ILanguage> T loadLanguage(String languageName, Class<T> languageType) {
        try {
            Constructor<T> constructor = languageType.getDeclaredConstructor(String.class); // get the ILanguage default constructor
            T newLanguage = constructor.newInstance(languageName);
            Debugger.getDebugger().info("Creating new instance of " + languageType + " !");
            return newLanguage;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            Debugger.getDebugger().error("No constructor was found for " + languageType + " !");
            return null;
        }
    }
}
