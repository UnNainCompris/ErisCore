package fr.eris.eriscore.utils.task;

import fr.eris.eriscore.ErisCore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

public class ErisTask extends BukkitRunnable {

    private ErisTaskAction todo;
    private ErisTaskAction cancelAction;
    private final boolean async;
    private final long repeatDelayTick, startDelayTick;

    @Getter private long tickSinceStart;

    public ErisTask(ErisTaskAction run, boolean isAsync) {
        this(run, null, isAsync, -1, -1);
    }

    public ErisTask(ErisTaskAction run, boolean isAsync, long startDelayTick) {
        this(run, null, isAsync, startDelayTick, -1);
    }

    public ErisTask(ErisTaskAction run, boolean isAsync, long startDelayTick, long repeatDelayTick) {
        this(run, null, isAsync, startDelayTick, repeatDelayTick);
    }

    public ErisTask(ErisTaskAction todo, ErisTaskAction cancelAction, boolean isAsync, long startDelayTick, long repeatDelayTick) {
        this.todo = todo;
        this.cancelAction = cancelAction;
        this.async = isAsync;
        this.startDelayTick = startDelayTick;
        this.repeatDelayTick = repeatDelayTick;
    }

    public void run() {
        if(todo != null) {
            tickSinceStart += 1;
            todo.call(this);
        }
    }

    public synchronized void cancel() {
        super.cancel();
        onCancel();
    }

    protected void onCancel() {
        if(cancelAction != null)
            cancelAction.call(this);
    }

    public ErisTask start() {
        if(async) {
            if(repeatDelayTick > 0) runTaskTimerAsynchronously(ErisCore.getInstance(), startDelayTick >= 0 ? startDelayTick : 0, repeatDelayTick);
            else if(startDelayTick >= 0) runTaskLaterAsynchronously(ErisCore.getInstance(), startDelayTick);
            else runTaskAsynchronously(ErisCore.getInstance());
        } else {
            if(repeatDelayTick > 0) runTaskTimer(ErisCore.getInstance(), startDelayTick >= 0 ? startDelayTick : 0, repeatDelayTick);
            else if(startDelayTick >= 0) runTaskLater(ErisCore.getInstance(), startDelayTick);
            else runTask(ErisCore.getInstance());
        }
        return this;
    }

    public ErisTask setCancelAction(ErisTaskAction newCancelAction) {
        this.cancelAction = newCancelAction;
        return this;
    }

    public ErisTask setAction(ErisTaskAction newAction) {
        this.todo = newAction;
        return this;
    }

    public interface ErisTaskAction {
        void call(ErisTask executionTask);
    }
}
