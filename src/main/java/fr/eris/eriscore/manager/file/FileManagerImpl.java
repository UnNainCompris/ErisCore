package fr.eris.eriscore.manager.file;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.file.FileManager;
import fr.eris.eriscore.api.manager.file.object.DataFile;
import fr.eris.eriscore.utils.task.ErisTask;
import fr.eris.eriscore.utils.task.TaskUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FileManagerImpl implements FileManager<ErisCore> {

    @Getter @Setter private ErisCore parent;

    private ErisTask savingTask;
    private final long taskRepeatDelay = 20L * 30L;

    public void start() {
        savingTask = TaskUtils.asyncRepeat((task) -> attemptSavingDataFile(task.getTickSinceStart()),
                        0L, taskRepeatDelay).setCancelAction((task) -> forceSaveLoadedDataFile());
    }

    public void attemptSavingDataFile(long currentTick) {
        for(DataFile currentDataFile : loadedData) {
            long normalizedLastSaveTick = currentTick - currentDataFile.getLastSaveTick();
            if(normalizedLastSaveTick == 0 || (currentDataFile.getSaveDelay() / normalizedLastSaveTick) >= 1 ||
                    (currentDataFile.getSaveDelay() / (normalizedLastSaveTick + taskRepeatDelay)) > 1) {
                currentDataFile.save();
                currentDataFile.setLastSaveTick(currentTick);
            }
        }
    }

    public void forceSaveLoadedDataFile() {
        for(DataFile currentData : loadedData) {
            if(currentData.isAllowingForceSave())
                currentData.save();
        }
    }

    public void stop() {
        savingTask.cancel();
    }

    public List<DataFile> retrieveLoadedDataFile() {
        return loadedData;
    }
}
