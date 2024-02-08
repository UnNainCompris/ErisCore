package fr.eris.eriscore.utils.data;

import fr.eris.eriscore.manager.config.ConfigManager;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.task.TaskUtils;

import java.io.File;

public abstract class IData {

    public final boolean asyncSaving;
    // The lowest save rate is every 20 * 30 tick | approximately every 30 second
    public final long autoSaveDelay;
    public boolean allowForceSave;
    public long lastAutoSaveTick;

    public IData() {
        this(true, -1);
        ConfigManager.appendNewData(this);
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
        Debugger.getDebugger("ErisCore")
                .info("Saving " + getSaveFileName() + " at " + getSaveDirectory().getAbsolutePath() + " ! {async: " + asyncSaving + "}");
        if(asyncSaving) TaskUtils.async((task) -> saveData());
        else TaskUtils.sync((task) -> saveData());
    }
}
