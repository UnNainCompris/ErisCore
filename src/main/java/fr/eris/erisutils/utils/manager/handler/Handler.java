package fr.eris.erisutils.utils.manager.handler;

import fr.eris.erisutils.utils.manager.Manager;
import lombok.Getter;

@Getter
public abstract class Handler<T> {
    protected T parent;

    public abstract void start();
    public abstract void stop();
}
