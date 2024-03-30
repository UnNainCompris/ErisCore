package fr.eris.eriscore.api.manager.commands;

import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.utils.Manager;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public interface CommandManager<T> extends Manager<T> {

    CommandMap retrieveCommandMap();

    <C extends ErisCommand> void registerCommand(C commandToRegister);
    void unRegisterCommand(String commandName);

    HashMap<String, ErisCommand> retrieveRegisteredCommand();
}
