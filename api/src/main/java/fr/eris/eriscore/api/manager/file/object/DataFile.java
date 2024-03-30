package fr.eris.eriscore.api.manager.file.object;

import java.io.File;

public interface DataFile {

    SaveType getSaveType();
    long getSaveDelay();
    boolean isAllowingForceSave();
    void setLastSaveTick(long tick);
    long getLastSaveTick();

    File getSaveDirectory();
    String getSaveFileName();
    void saveData();

    void save();

}
