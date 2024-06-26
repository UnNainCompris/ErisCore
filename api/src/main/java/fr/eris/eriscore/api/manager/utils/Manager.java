package fr.eris.eriscore.api.manager.utils;

public interface Manager<T> {
    T getParent();
    void setParent(T newParent);

    void start();
    void stop();
}
