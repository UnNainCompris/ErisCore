package fr.eris.eriscore.manager.command;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.commands.TestCommand;
import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.nms.NmsAdaptaterManager;
import fr.eris.eriscore.utils.manager.Manager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public class CommandManager extends Manager<ErisCore> {

    private CommandMap commandMap;
    private final HashMap<String, IErisCommand> registeredCommand = new HashMap<>();

    public void start() {
        commandMap = NmsAdaptaterManager.getNmsSupport().retrieveCommandMap();
        new TestCommand();
    }

    public void stop() {

    }

    public void registerCommand(IErisCommand newCommandToRegister) {
        if(registeredCommand.containsKey(newCommandToRegister.getName().toLowerCase())) {
            Debugger.getDebugger("ErisCore").severe("Two commannd with the same name try to get registered ! " +
                    "{" + newCommandToRegister.getName().toLowerCase() + "}");
            return;
        }
        Debugger.getDebugger("ErisCore").info("Registering new Command ! {" + newCommandToRegister.getName() + "}");
        commandMap.register("eriscore", newCommandToRegister);
    }

    public void unRegisterCommand(String commandName) {
        IErisCommand foundCommand = registeredCommand.get(commandName);
        if(foundCommand == null) {
            Debugger.getDebugger("ErisCore").warning("Unable to find " + commandName + " command to unregister it !");
            return;
        }
        foundCommand.unregister(commandMap);
    }
}
