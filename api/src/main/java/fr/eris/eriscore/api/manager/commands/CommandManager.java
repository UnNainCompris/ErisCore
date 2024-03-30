package fr.eris.eriscore.api.manager.commands;

import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.utils.ApiClassRedirect;
import fr.eris.eriscore.api.manager.utils.Manager;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

@ApiClassRedirect("fr.eris.eriscore.manager.command.CommandManagerImpl")
public interface CommandManager<T> extends Manager<T> {

    CommandMap retrieveCommandMap();

    <C extends ErisCommand> void registerCommand(C commandToRegister);
    void unregisterCommand(String commandName);

    HashMap<String, ErisCommand> retrieveRegisteredCommand();
}
