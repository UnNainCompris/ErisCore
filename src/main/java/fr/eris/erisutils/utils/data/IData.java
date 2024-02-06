package fr.eris.erisutils.utils.data;

import java.io.File;

public abstract class IData {
    public abstract File getSaveDirectory();
    public abstract String getSaveFileName();
    public abstract void save();
    public static <T extends IData> T loadData(Class<T> dataType, File dataFile) {
        return null;
    }
}
