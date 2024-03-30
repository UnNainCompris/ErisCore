package fr.eris.eriscore.api.manager.language;

import fr.eris.eriscore.api.manager.language.object.Language;
import fr.eris.eriscore.api.manager.utils.ApiClassRedirect;
import fr.eris.eriscore.api.manager.utils.Manager;

@ApiClassRedirect("fr.eris.eriscore.manager.language.LanguageManagerImpl")
public interface LanguageManager<T> extends Manager<T> {
    <L extends Language> L getLanguage(String languageName, Class<L> languageType);

    void testThings();
}
