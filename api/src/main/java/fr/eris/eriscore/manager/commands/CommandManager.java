package fr.eris.eriscore.manager.commands;

import fr.eris.eriscore.manager.utils.Manager;
import org.bukkit.command.CommandMap;

import java.util.HashMap;
import java.util.Objects;

public interface CommandManager<T> extends Manager<T> {

    CommandMap retrieveCommandMap();

    void registerCommand();
    void unRegisterCommand();

    HashMap<String, Object> retrieveRegisteredCommand();
}
