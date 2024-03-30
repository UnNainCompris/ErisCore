package fr.eris.eriscore.manager.command;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.api.manager.commands.CommandManager;
import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.nms.NmsSupportManagerImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandMap;

import java.util.HashMap;

public class CommandManagerImpl implements CommandManager<ErisCore> {

    @Getter @Setter private ErisCore parent;
    private CommandMap commandMap;
    private final HashMap<String, ErisCommand> registeredCommand = new HashMap<>();

    public void start() {
        commandMap = ErisCore.getNmsSupportManager().getNmsSupport().retrieveCommandMap();
    }

    public void stop() {

    }

    public void unregisterCommand(String commandName) {
        ErisCommand foundCommand = registeredCommand.get(commandName);
        if(foundCommand == null) {
            ErisCore.getDebugger().warning("Unable to find " + commandName + " command to unregister it !");
            return;
        }
        foundCommand.unregisterCommand();
    }

    public CommandMap retrieveCommandMap() {
        return this.commandMap;
    }

    public void registerCommand(ErisCommand newCommandToRegister) {
        if(registeredCommand.containsKey(newCommandToRegister.getName().toLowerCase())) {
            ErisCore.getDebugger().severe("Two commannd with the same name try to get registered ! " +
                    "{" + newCommandToRegister.getName().toLowerCase() + "}");
            return;
        }
        ErisCore.getDebugger().info("Registering new Command ! {" + newCommandToRegister.getName() + "}");
        commandMap.register("eriscore", (IErisCommand)newCommandToRegister);
    }

    public HashMap<String, ErisCommand> retrieveRegisteredCommand() {
        return this.registeredCommand;
    }
}
