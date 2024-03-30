package fr.eris.eriscore.utils.data;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.file.FileManager;
import fr.eris.eriscore.api.manager.file.object.DataFile;
import fr.eris.eriscore.api.manager.file.object.SaveType;
import fr.eris.eriscore.manager.file.FileManagerImpl;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.task.TaskUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
public abstract class IData implements DataFile {

    public final SaveType saveType;
    // The lowest save rate is every 20 * 30 tick | approximately every 30 second
    public final long saveDelay;
    public boolean allowingForceSave;
    @Setter public long lastSaveTick;

    public IData() {
        this(SaveType.ASYNC, -1);
        FileManager.appendNewDataFile(this);
    }

    public IData(long saveDelay) {
        this(SaveType.ASYNC, saveDelay);
    }

    public IData(SaveType asyncSaving) {
        this(asyncSaving, -1);
    }

    public IData(SaveType saveType, long saveDelay) {
        this.saveType = saveType;
        this.saveDelay = saveDelay;
    }

    public abstract File getSaveDirectory();
    public abstract String getSaveFileName();
    public abstract void saveData();

    public final void save() {
        if(saveType == SaveType.ASYNC) TaskUtils.async((task) -> processSaving());
        else if(saveType == SaveType.SYNC) TaskUtils.sync((task) -> processSaving());
    }

    private void processSaving() {
        long startSaveTime = System.currentTimeMillis();
        saveData();
        ErisCore.getDebugger()
                .info("Saved " + getSaveFileName() + " at " + getSaveDirectory().getAbsolutePath() + " in "
                        + (System.currentTimeMillis() - startSaveTime) + " ms! {saveType: " + saveType + "}");
    }
}
