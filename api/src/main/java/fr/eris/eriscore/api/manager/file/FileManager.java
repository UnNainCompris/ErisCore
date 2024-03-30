package fr.eris.eriscore.api.manager.file;

import fr.eris.eriscore.api.manager.file.object.DataFile;
import fr.eris.eriscore.api.manager.utils.ApiClassRedirect;
import fr.eris.eriscore.api.manager.utils.Manager;

import java.util.ArrayList;
import java.util.List;

@ApiClassRedirect("fr.eris.eriscore.manager.file.FileManagerImpl")
public interface FileManager<T> extends Manager<T> {

    List<DataFile> loadedData = new ArrayList<>();

    void forceSaveLoadedDataFile();

    static void appendNewDataFile(DataFile newDataFile) {
        loadedData.add(newDataFile);
    }

    static boolean unloadDataFile(DataFile dataFileToUnload) {
        return loadedData.remove(dataFileToUnload);
    }

    void attemptSavingDataFile(long currentTick);

    List<DataFile> retrieveLoadedDataFile();
}
