package fr.eris.erisutils.utils.data;

import fr.eris.erisutils.utils.task.TaskUtils;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public abstract class IData {

    public final boolean asyncSaving;
    public final long autoSaveDelay;

    public IData() {
        this(true, -1);
    }

    public IData(long autoSaveDelay) {
        this(true, autoSaveDelay);
    }

    public IData(boolean asyncSaving) {
        this(asyncSaving, -1);
    }

    public IData(boolean asyncSaving, long autoSaveDelay) {
        this.asyncSaving = asyncSaving;
        this.autoSaveDelay = autoSaveDelay;
    }

    public abstract File getSaveDirectory();
    public abstract String getSaveFileName();
    protected abstract void saveData();

    public final void save() {
        if(asyncSaving) TaskUtils.async(this::saveData);
        else TaskUtils.sync(this::saveData);
    }
}
