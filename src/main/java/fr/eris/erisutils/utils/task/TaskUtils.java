package fr.eris.erisutils.utils.task;

public class TaskUtils {

    public static ErisTask sync(ErisTask.ErisTaskAction todo) {
        return new ErisTask(todo, false).start();
    }

    public static ErisTask syncLater(ErisTask.ErisTaskAction todo, long startDelay) {
        return new ErisTask(todo, false, startDelay).start();
    }

    public static ErisTask syncRepeat(ErisTask.ErisTaskAction todo, long startDelay, long repeatDelay) {
        return new ErisTask(todo, false, startDelay, repeatDelay).start();
    }

    public static ErisTask async(ErisTask.ErisTaskAction todo) {
        return new ErisTask(todo, true).start();
    }

    public static ErisTask asyncLater(ErisTask.ErisTaskAction todo, long startDelay) {
        return new ErisTask(todo, true, startDelay).start();
    }

    public static ErisTask asyncRepeat(ErisTask.ErisTaskAction todo, long startDelay, long repeatDelay) {
        return new ErisTask(todo, true, startDelay, repeatDelay).start();
    }
}
