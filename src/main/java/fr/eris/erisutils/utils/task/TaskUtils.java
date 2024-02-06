package fr.eris.erisutils.utils.task;

public class TaskUtils {

    public static ErisTask sync(Runnable todo) {
        return new ErisTask(todo, false).start();
    }

    public static ErisTask syncLater(Runnable todo, long startDelay) {
        return new ErisTask(todo, false, startDelay).start();
    }

    public static ErisTask syncRepeat(Runnable todo, long startDelay, long repeatDelay) {
        return new ErisTask(todo, false, startDelay, repeatDelay).start();
    }

    public static ErisTask async(Runnable todo) {
        return new ErisTask(todo, true).start();
    }

    public static ErisTask asyncLater(Runnable todo, long startDelay) {
        return new ErisTask(todo, true, startDelay).start();
    }

    public static ErisTask asyncRepeat(Runnable todo, long startDelay, long repeatDelay) {
        return new ErisTask(todo, true, startDelay, repeatDelay).start();
    }
}
