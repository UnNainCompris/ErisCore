package fr.eris.eriscore.manager.command.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class IErisCommand extends BukkitCommand {

    private final HashMap<String, IErisCommandArgument> registeredArgument;
    private final List<IErisCommand> subCommand;
    private IErisCommand parentCommand;
    private final AvailableSender availableSender; // The available sender type for this command

    public IErisCommand(String name, AvailableSender availableSender, String permission, List<String> aliases) {
        super(name);

        this.subCommand = new ArrayList<>();
        this.registeredArgument = new HashMap<>();

        setPermission(permission == null ? "" : permission);
        this.availableSender = availableSender;
        if(aliases != null)
            setAliases(aliases);
        registerSubCommand();
        registerCommand();
    }

    public void registerCommand() {
        if(!isSubcommand())
            ErisCore.getCommandManager().registerCommand(this);
    }

    /**
     *  The place where you should add all you sub command using {@link #addSubcommand(IErisCommand)}
     */
    public abstract void registerSubCommand();

    public void addSubcommand(IErisCommand newSubcommand) {
        if(newSubcommand.isSubcommand()) {
            Debugger.getDebugger("ErisCore").severe("Try to register a command as subcommand that " +
                    "is already a subcommand ! {" + newSubcommand.getName() + "}");
            return;
        }
        subCommand.add(newSubcommand);
        newSubcommand.parentCommand = this;
    }

    public boolean isSubcommand() {
        return parentCommand != null;
    }

    public abstract void execute(CommandSender sender, String executionIdentifier);
    public abstract void handleError();

    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        processArgument(args);
        execute(commandSender, "");
        return true;
    }

    public List<PossibleExecution> processArgument(String[] args) {
        List<PossibleExecution> possibleExecutions = new ArrayList<>();


        return possibleExecutions;
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Debugger.getDebugger("ErisCore").info(alias);
        Debugger.getDebugger("ErisCore").info(Arrays.toString(args));
        return new ArrayList<>();
    }

    public static enum AvailableSender {
        CONSOLE_ONLY, PLAYER_ONLY, CONSOLE_AND_PLAYER, NONE;
    }

    public static class PossibleExecution {

    }
}
