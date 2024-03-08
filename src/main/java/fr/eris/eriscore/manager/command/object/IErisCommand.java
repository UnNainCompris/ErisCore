package fr.eris.eriscore.manager.command.object;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.commands.object.ErisCommand;
import fr.eris.eriscore.manager.commands.object.argument.ErisCommandArgument;
import fr.eris.eriscore.manager.commands.object.error.ExecutionError;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.storage.Tuple;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class IErisCommand extends BukkitCommand implements ErisCommand {

    private final List<ErisCommandArgument<?>> registeredArgument;
    private final HashMap<String, ErisCommand> subCommands;
    @Setter private ErisCommand parentCommand;
    private final AvailableSender availableSender; // The available sender type for this command

    public IErisCommand(String name, AvailableSender availableSender, String permission, List<String> aliases) {
        super(name, "", "", aliases == null ? Collections.emptyList() : aliases);

        this.subCommands = new HashMap<>();
        this.registeredArgument = new ArrayList<>();

        setPermission(permission == null ? "" : permission);
        this.availableSender = availableSender;

        for(ErisCommand command : registerSubCommand())
            addSubcommand(command);
        for(ErisCommandArgument<?> commandArgument : registerCommandArgument())
            addCommandArgument(commandArgument);

        validateCommandArguments();
    }

    public void validateCommandArguments() {
        for(ErisCommandArgument<?> entry : registeredArgument) {
            if(entry.isNullable()) {
                if(registeredArgument.indexOf(entry) != registeredArgument.size() - 1) {
                    Debugger.getDebugger().severe("An argument that is null was found but he is not at the end !");
                    return;
                }
            }
        }
    }

    public void registerCommand() {
        if(!isSubcommand())
            ErisCore.getCommandManager().registerCommand(this);
    }

    public void unregisterCommand() {
        super.unregister(ErisCore.getCommandManager().retrieveCommandMap());
    }

    public abstract Collection<ErisCommand> registerSubCommand();
    public abstract Collection<ErisCommandArgument<?>> registerCommandArgument();

    public void addSubcommand(ErisCommand newSubcommand) {
        if(newSubcommand.isSubcommand()) {
            Debugger.getDebugger().severe("Try to register a command as subcommand that " +
                    "is already a subcommand ! {" + newSubcommand.getName() + "}");
            return;
        }
        subCommands.put(newSubcommand.getName().toLowerCase(), newSubcommand);
        newSubcommand.setParentCommand(this);
    }

    public void addCommandArgument(ErisCommandArgument<?> newCommandArgument) {
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
        resetArgumentValue();

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

    public void resetArgumentValue() {
        for(ErisCommandArgument<?> commandArgument : registeredArgument) {
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
        boolean isLastNullable = !registeredArgument.isEmpty() && registeredArgument.get(registeredArgument.size()-1).isNullable();

        if((args.length < registeredArgument.size() && !isLastNullable) ||
            (isLastNullable && args.length < registeredArgument.size() - 1)) {
            handleError(commandSender, ExecutionError.NOT_ENOUGH_ARGS, args);
            return false;
        } else {
            for(int i = 0 ; i < args.length ; i++) {
                if(i >= registeredArgument.size()) break;
                String currentArg = args[i];
                ErisCommandArgument<?> commandArgument = registeredArgument.get(i);
                if(!commandArgument.isArgumentValid(commandSender, currentArg)) {
                    handleError(commandSender, ExecutionError.INVALID_ARGS_VALUE, args);
                    return false;
                }
                commandArgument.setValue(commandSender, args[i]);
            }
        }

        return true;
    }

    public <T extends ErisCommandArgument<?>> T retrieveArgument(Class<T> requireArgument, String argumentName) {
        argumentName = argumentName.toLowerCase();
        ErisCommandArgument<?> foundArguments = getArgumentByName(argumentName);
        if(foundArguments != null && foundArguments.getClass().isAssignableFrom(requireArgument))
            return requireArgument.cast(foundArguments);
        return null;
    }

    public ErisCommandArgument<?> getArgumentByName(String argumentName) {
        for(ErisCommandArgument<?> argument: registeredArgument) {
            if(argument.getName().equalsIgnoreCase(argumentName)) return argument;
        }
        return null;
    }

    public <T extends ErisCommandArgument<V>, V> V retrieveArgumentValue(Class<T> requireArgument, String argumentName) {
        argumentName = argumentName.toLowerCase();
        ErisCommandArgument<V> foundArguments = retrieveArgument(requireArgument, argumentName);
        if (foundArguments != null) {
            return foundArguments.getCurrentExecutionValue();
        }
        return null;
    }

    public ErisCommand getSubcommandByName(String subcommandName) {
        ErisCommand foundSubCommand = subCommands.get(subcommandName.toLowerCase());
        if(foundSubCommand != null) return foundSubCommand;

        for(ErisCommand subcommand : this.subCommands.values()) {
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
        IErisCommand tempSubCommand = (IErisCommand) getSubcommandByName(arguments[0]);
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
            ErisCommandArgument<?> commandArgument = registeredArgument.get(args.length - 1);
            for(String choice : commandArgument.retrieveChoices(sender)) {
                if(choice.toLowerCase().startsWith(lastArgs.toLowerCase())) {
                    tabCompleteOption.add(choice);
                }
            }
        }

        for(Map.Entry<String, ErisCommand> subcommandEntry : subCommands.entrySet()) {
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
