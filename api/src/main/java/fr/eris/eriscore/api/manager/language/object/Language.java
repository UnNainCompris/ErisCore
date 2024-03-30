package fr.eris.eriscore.api.manager.language.object;

import fr.eris.eriscore.api.manager.file.object.DataFile;

public interface Language extends DataFile {

    String getLanguageName();

    void load();
    String getLang(String path);
}
