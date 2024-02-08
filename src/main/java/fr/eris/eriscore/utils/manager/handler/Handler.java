package fr.eris.eriscore.utils.manager.handler;

import lombok.Getter;

@Getter
public abstract class Handler<T> {
    protected T parent;

    public abstract void start();
    public abstract void stop();
}
