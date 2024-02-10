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
        if(asyncSaving) TaskUtils.async((task) -> processSaving());
        else TaskUtils.sync((task) -> processSaving());
    }

    private void processSaving() {
        long startSaveTime = System.currentTimeMillis();
        saveData();
        Debugger.getDebugger("ErisCore")
                .info("Saved " + getSaveFileName() + " at " + getSaveDirectory().getAbsolutePath() + " in "
                        + (System.currentTimeMillis() - startSaveTime) + " ms! {async: " + asyncSaving + "}");
    }
}
