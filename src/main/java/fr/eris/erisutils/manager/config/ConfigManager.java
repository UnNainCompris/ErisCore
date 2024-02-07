package fr.eris.erisutils.manager.config;

import fr.eris.erisutils.ErisCore;
import fr.eris.erisutils.utils.data.IData;
import fr.eris.erisutils.utils.manager.Manager;
import fr.eris.erisutils.utils.task.ErisTask;
import fr.eris.erisutils.utils.task.TaskUtils;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager extends Manager<ErisCore> {

    private final List<IData> loadedData = new ArrayList<>();
    private ErisTask savingTask;
    private final long taskRepeatDelay = 20L * 30L;

    public void start() {
        savingTask = TaskUtils.asyncRepeat((task) -> attemptSaving(task.getTickSinceStart()),
                        0L, taskRepeatDelay)
                                .setCancelAction((task) -> forceSaveAll());
    }

    public void attemptSaving(long currentTick) {
        for(IData currentData : loadedData) {
            long normalizedLastSaveTick = currentTick - currentData.lastAutoSaveTick;
            if(currentData.autoSaveDelay / normalizedLastSaveTick >= 1 ||
                    currentData.autoSaveDelay / (normalizedLastSaveTick + taskRepeatDelay) > 1) {
                currentData.save();
                currentData.lastAutoSaveTick = currentTick;
            }
        }
    }

    public void forceSaveAll() {
        for(IData currentData : loadedData) {
            if(currentData.allowForceSave)
                currentData.save();
        }
    }

    public void stop() {
        savingTask.cancel();
    }

    public void appendNewData(IData newData) {
        loadedData.add(newData);
    }

    public boolean removeLoadedData(IData dataToUnload) {
        return loadedData.remove(dataToUnload);
    }
}
