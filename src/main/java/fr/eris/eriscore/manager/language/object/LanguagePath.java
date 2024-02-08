package fr.eris.eriscore.manager.language.object;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LanguagePath {
    String path();
}
