package fr.eris.eriscore.api.manager.commands.object;

import fr.eris.eriscore.api.manager.commands.object.argument.ErisCommandArgument;
import fr.eris.eriscore.api.manager.commands.object.error.ExecutionError;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface ErisCommand {

    String getName();
    Collection<String> getAliases();

    void validateCommandArguments();
    void setParentCommand(ErisCommand newParentCommand);

    boolean isSubcommand();
    void registerCommand();
    void unregisterCommand();

    Collection<ErisCommand> registerSubCommand();
    Collection<ErisCommandArgument<?>> registerCommandArgument();

    void execute(CommandSender sender);
    void handleError(CommandSender sender, ExecutionError error, String[] rawArguments);

    void resetArgumentValue();

    boolean processPermission(CommandSender commandSender, String[] args);
    boolean processSender(CommandSender commandSender, String[] args);
    boolean processArguments(CommandSender commandSender, String[] args);

    ErisCommandArgument<?> getArgumentByName(String argumentName);
    <T extends ErisCommandArgument<V>, V> V retrieveArgumentValue(Class<T> requireArgument, String argumentName);

    ErisCommand getSubcommandByName(String subcommandName);
}
