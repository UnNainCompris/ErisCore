package fr.eris.erisutils.utils.manager;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class Manager<T> {
    protected T parent;

    public abstract void start();
    public abstract void stop();
}
