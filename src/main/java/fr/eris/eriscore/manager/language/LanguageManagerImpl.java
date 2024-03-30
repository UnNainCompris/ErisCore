package fr.eris.eriscore.manager.language;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.api.manager.language.LanguageManager;
import fr.eris.eriscore.api.manager.language.object.Language;
import fr.eris.eriscore.manager.language.object.ILanguage;
import fr.eris.eriscore.api.manager.utils.Manager;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class LanguageManagerImpl implements LanguageManager<ErisCore> {

    private final HashMap<String, Language> loadedLanguage = new HashMap<>();
    @Getter @Setter private ErisCore parent;

    public void start() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    public void stop() {

    }

    public void testThings() {
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
        System.out.println("REOUJFGHZERIOUFHZEIYFH");
    }

    public <T extends Language> T getLanguage(String languageName, Class<T> languageType) {
        languageName = languageName.toLowerCase();

        if(!loadedLanguage.containsKey(languageName)) {
            T newLanguage = loadLanguage(languageName, languageType);
            loadedLanguage.put(languageName, newLanguage);
            ErisCore.getDebugger().info("Language " + languageName + "is now loaded !");
        } else {
            if(!languageType.isAssignableFrom(loadedLanguage.get(languageName).getClass())) {
                ErisCore.getDebugger().error("A language with the same name but not the same type is already loaded");
                throw new IllegalArgumentException();
            }
        }
        return languageType.cast(loadedLanguage.get(languageName));
    }

    private <T extends Language> T loadLanguage(String languageName, Class<T> languageType) {
        try {
            Constructor<T> constructor = languageType.getDeclaredConstructor(String.class); // get the ILanguage default constructor
            T newLanguage = constructor.newInstance(languageName);
            newLanguage.load();
            ErisCore.getDebugger().info("Creating new instance of " + languageType + " !");
            return newLanguage;
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            ErisCore.getDebugger().error("No constructor was found for " + languageType + " !");
            return null;
        }
    }
}
