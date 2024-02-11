package fr.eris.eriscore.manager.command.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.command.object.error.ExecutionError;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.storage.Tuple;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.*;

public abstract class IErisCommand extends BukkitCommand {

    private final HashMap<String, IErisCommandArgument<?>> registeredArgument;
    private final HashMap<String, IErisCommand> subCommands;
    private IErisCommand parentCommand;
    private final AvailableSender availableSender; // The available sender type for this command

    public IErisCommand(String name, AvailableSender availableSender, String permission, List<String> aliases) {
        super(name);

        this.subCommands = new HashMap<>();
        this.registeredArgument = new HashMap<>();

        setPermission(permission == null ? "" : permission);
        this.availableSender = availableSender;
        if(aliases != null)
            setAliases(aliases);
        registerSubCommand();
    }

    public void registerCommand() {
        if(!isSubcommand())
            ErisCore.getCommandManager().registerCommand(this);
    }

    /**
     *  The place where you should add all you sub command using {@link #addSubcommand(IErisCommand)}
     */
    public abstract void registerSubCommand();
    public abstract void registerCommandArgument();

    public void addSubcommand(IErisCommand newSubcommand) {
        if(newSubcommand.isSubcommand()) {
            Debugger.getDebugger("ErisCore").severe("Try to register a command as subcommand that " +
                    "is already a subcommand ! {" + newSubcommand.getName() + "}");
            return;
        }
        subCommands.put(newSubcommand.getName().toLowerCase(), newSubcommand);
        newSubcommand.parentCommand = this;
    }

    public void addCommandArgument(IErisCommandArgument<?> newCommandArgument) {
        if(newCommandArgument.getParentCommand() != null) {
            Debugger.getDebugger("ErisCore").severe("Try to register a command argument that " +
                    "is already a register ! {" + newCommandArgument.getName() + "}");
            return;
        }
        registeredArgument.put(newCommandArgument.getName().toLowerCase(), newCommandArgument);
        newCommandArgument.setParentCommand(this);
    }

    public boolean isSubcommand() {
        return parentCommand != null;
    }

    public abstract void execute(CommandSender sender);
    public abstract void handleError(CommandSender sender, ExecutionError error, String[] args);

    public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
        Tuple<Integer, IErisCommand> currentSubCommandDepth = findSubCommandFromArgs(args, 0);
        if(currentSubCommandDepth.getA() != 0)
            return currentSubCommandDepth.getB().execute(commandSender, commandLabel, args);



        execute(commandSender);
        return true;
    }

    public int countNullableArgument() {
        return 1;
    }

    public IErisCommand getSubCommand(String subcommandName) {
        IErisCommand foundSubCommand = subCommands.get(subcommandName.toLowerCase());
        if(foundSubCommand != null) return foundSubCommand;

        for(IErisCommand subcommand : this.subCommands.values()) {
            for (String aliases : this.getAliases()) {
                if (aliases.equalsIgnoreCase(subcommandName)) {
                    return subcommand;
                }
            }
        }

        return null;
    }

    public Tuple<Integer, IErisCommand> findSubCommandFromArgs(String[] arguments, int currentDepth) {
        if(arguments == null || arguments.length == 0) return new Tuple<>(currentDepth, this);
        currentDepth += 1;
        IErisCommand tempSubCommand = getSubCommand(arguments[0]);
        return tempSubCommand != null ?
                tempSubCommand.findSubCommandFromArgs(Arrays.copyOfRange(arguments, 1, arguments.length), currentDepth) :
                new Tuple<>(currentDepth - 1, this);
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> tabCompleteOption = new ArrayList<>();

        Tuple<Integer, IErisCommand> currentSubCommandDepth = findSubCommandFromArgs(args, 0);
        if(currentSubCommandDepth.getA() != 0) {
            return currentSubCommandDepth.getB().tabComplete(sender, alias,
                    Arrays.copyOfRange(args, currentSubCommandDepth.getA(), args.length));
        }
        String lastArgs = "";
        if(args.length > 0)
            lastArgs = args[args.length - 1];
        for(Map.Entry<String, IErisCommand> subcommandEntry : subCommands.entrySet()) {
            if(subcommandEntry.getValue().getName().toLowerCase().startsWith(lastArgs.toLowerCase())) { // check for subcommand name
                tabCompleteOption.add(subcommandEntry.getValue().getName());
            }
            for(String subcommandAliases : subcommandEntry.getValue().getAliases()) {
                if(subcommandAliases.toLowerCase().startsWith(lastArgs.toLowerCase())) { // check for subcommand aliases
                    tabCompleteOption.add(subcommandAliases);
                }
            }
        }

        return tabCompleteOption;
    }

    public enum AvailableSender {
        CONSOLE_ONLY, PLAYER_ONLY, CONSOLE_AND_PLAYER, NONE;
    }
}
