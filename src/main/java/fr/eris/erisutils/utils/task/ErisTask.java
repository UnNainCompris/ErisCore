package fr.eris.erisutils.utils.task;

import fr.eris.erisutils.ErisCore;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

public class ErisTask extends BukkitRunnable {

    private final ErisTaskAction todo;
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
        if(todo == null) {
            throw new IllegalArgumentException("A todo action cannot be null in ErisTask !");
        }
        this.todo = todo;
        this.cancelAction = cancelAction;
        this.async = isAsync;
        this.startDelayTick = startDelayTick;
        this.repeatDelayTick = repeatDelayTick;
    }

    public void run() {
        todo.call(this);
        tickSinceStart += 1;
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

    public interface ErisTaskAction {
        void call(ErisTask executionTask);
    }
}
