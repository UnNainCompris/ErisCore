package fr.eris.eriscore.utils.manager;

import lombok.Getter;

@Getter
public abstract class Manager<T> {
    protected T parent;

    public abstract void start();
    public abstract void stop();
}
