package fr.eris.eriscore.api.manager.utils.handler;

public interface Handler<T> {
    T getParent();
    void setParent(T newParent);

    void start();
    void stop();
}
