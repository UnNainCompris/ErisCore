package fr.eris.eriscore.manager.utils.handler;

import lombok.Getter;

public interface Handler<T> {
    T getParent();
    void setParent(T newParent);

    void start();
    void stop();
}
