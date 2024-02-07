package fr.eris.erisutils.utils.data;

import fr.eris.erisutils.ErisCore;
import fr.eris.erisutils.manager.config.ConfigManager;
import fr.eris.erisutils.utils.task.TaskUtils;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public abstract class IData {

    public final boolean asyncSaving;
    // The lowest save rate is every 20 * 30 tick | approximately every 30 second
    public final long autoSaveDelay;
    public boolean allowForceSave;
    public long lastAutoSaveTick;

    public IData() {
        this(true, -1);
        ErisCore.getConfigManager().appendNewData(this);
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
        if(asyncSaving) TaskUtils.async((task) -> saveData());
        else TaskUtils.sync((task) -> saveData());
    }
}
