package fr.eris.eriscore.api.manager.language.object;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LanguagePath {
    String value(); // language path ex: "player.killmessage.otherplayer"
}
