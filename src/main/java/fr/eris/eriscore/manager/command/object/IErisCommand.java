package fr.eris.eriscore.manager.command.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.command.object.error.ExecutionError;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.storage.Tuple;
import fr.eris.eriscore.utils.task.TaskUtils;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class IErisCommand extends BukkitCommand {

    private final List<IErisCommandArgument<?>> registeredArgument;
    private final HashMap<String, IErisCommand> subCommands;
    private IErisCommand parentCommand;
    private final AvailableSender availableSender; // The available sender type for this command
    private boolean canRegister = true;

    public IErisCommand(String name, AvailableSender availableSender, String permission, List<String> aliases) {
        super(name, "", "", aliases == null ? Collections.emptyList() : aliases);

        this.subCommands = new HashMap<>();
        this.registeredArgument = new ArrayList<>();

        setPermission(permission == null ? "" : permission);
        this.availableSender = availableSender;

        TaskUtils.asyncLater((task) -> {
            registerSubCommand();
            registerCommandArgument();
            validateCommandArgument();
            if(canRegister) {
                registerCommand();
            } else {
                if(!isSubcommand())
                    Debugger.getDebugger().error("Error while registering a command ! {" + name + "}");
            }
        }, 1L);
    }

    public void validateCommandArgument() {
        for(IErisCommandArgument<?> entry : registeredArgument) {
            if(entry.isCanBeNull()) {
                if(registeredArgument.indexOf(entry) != registeredArgument.size() - 1) {
                    Debugger.getDebugger().severe("An argument that is null was found but he is not at the end !");
                    canRegister = false;
                    return;
                }
            }
        }
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
            Debugger.getDebugger().severe("Try to register a command as subcommand that " +
                    "is already a subcommand ! {" + newSubcommand.getName() + "}");
            return;
        }
        subCommands.put(newSubcommand.getName().toLowerCase(), newSubcommand);
        newSubcommand.parentCommand = this;
    }

    public void addCommandArgument(IErisCommandArgument<?> newCommandArgument) {
        if(newCommandArgument.getParentCommand() != null) {
            Debugger.getDebugger().severe("Try to register a command argument that " +
                    "is already a register ! {" + newCommandArgument.getName() + "}");
            return;
        }
        registeredArgument.add(newCommandArgument);
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
            return currentSubCommandDepth.getB().execute(commandSender, commandLabel,
                    Arrays.copyOfRange(args, currentSubCommandDepth.getA(), args.length));
        resetArgument();

        if(!processPermission(commandSender, args)) {
            return true;
        }

        if(!processSender(commandSender, args)) {
            return true;
        }

        if(!processArguments(commandSender, args)) {
            return true;
        }

        execute(commandSender);
        return true;
    }

    public void resetArgument() {
        for(IErisCommandArgument<?> commandArgument : registeredArgument) {
            commandArgument.resetValue();
        }
    }

    public boolean processPermission(CommandSender commandSender, String[] args) {
        if(getPermission() == null || commandSender.hasPermission(getPermission())) {
            return true;
        }
        handleError(commandSender, ExecutionError.INSUFFICIENT_PERMISSION, args);
        return false;
    }

    public boolean processSender(CommandSender commandSender, String[] args) {
        boolean isPlayer = commandSender instanceof Player;
        boolean isConsole = commandSender instanceof ConsoleCommandSender;

        if(isConsole && (availableSender == AvailableSender.CONSOLE_ONLY ||
                availableSender == AvailableSender.CONSOLE_AND_PLAYER)) {
            return true;
        }

        if(isPlayer && (availableSender == AvailableSender.PLAYER_ONLY ||
            availableSender == AvailableSender.CONSOLE_AND_PLAYER)) {
            return true;
        }

        handleError(commandSender, ExecutionError.INVALID_SENDER_TYPE, args);
        return false;
    }

    public boolean processArguments(CommandSender commandSender, String[] args) {
        boolean isLastNullable = !registeredArgument.isEmpty() && registeredArgument.get(registeredArgument.size()-1).isCanBeNull();

        if((args.length < registeredArgument.size() && !isLastNullable) ||
            (isLastNullable && args.length < registeredArgument.size() - 1)) {
            handleError(commandSender, ExecutionError.NOT_ENOUGH_ARGS, args);
            return false;
        } else {
            for(int i = 0 ; i < args.length ; i++) {
                if(i >= registeredArgument.size()) break;
                String currentArg = args[i];
                IErisCommandArgument<?> commandArgument = registeredArgument.get(i);
                if(!commandArgument.isValid(commandSender, currentArg)) {
                    handleError(commandSender, ExecutionError.INVALID_ARGS_VALUE, args);
                    return false;
                }
                commandArgument.setValue(commandSender, args[i]);
            }
        }

        return true;
    }

    public <T extends IErisCommandArgument<?>> T retrieveArgument(Class<T> requireArgument, String argumentName) {
        argumentName = argumentName.toLowerCase();
        IErisCommandArgument<?> foundArguments = getArgumentByName(argumentName);
        if(foundArguments != null && foundArguments.getClass().isAssignableFrom(requireArgument))
            return requireArgument.cast(foundArguments);
        return null;
    }

    public IErisCommandArgument<?> getArgumentByName(String argumentName) {
        for(IErisCommandArgument<?> argument: registeredArgument) {
            if(argument.getName().equalsIgnoreCase(argumentName)) return argument;
        }
        return null;
    }

    public <T extends IErisCommandArgument<V>, V> V retrieveArgumentValue(Class<T> requireArgument, String argumentName) {
        argumentName = argumentName.toLowerCase();
        IErisCommandArgument<V> foundArguments = retrieveArgument(requireArgument, argumentName);
        if(foundArguments != null) {
            return foundArguments.getLastExecutionValue();
        }
        return null;
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
        if(!processPermission(sender, args)) {
            return Collections.emptyList();
        }
        String lastArgs = "";
        if(args.length > 0)
            lastArgs = args[args.length - 1];

        if(registeredArgument.size() >= args.length) {
            IErisCommandArgument<?> commandArgument = registeredArgument.get(args.length - 1);
            for(String choice : commandArgument.getChoices(sender)) {
                if(choice.toLowerCase().startsWith(lastArgs.toLowerCase())) {
                    tabCompleteOption.add(choice);
                }
            }
        }

        for(Map.Entry<String, IErisCommand> subcommandEntry : subCommands.entrySet()) {
            if(args.length > 1) break;
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
