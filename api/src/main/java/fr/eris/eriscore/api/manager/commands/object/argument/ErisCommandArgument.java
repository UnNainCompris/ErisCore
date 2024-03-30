package fr.eris.eriscore.api.manager.commands.object.argument;

import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.commands.object.argument.choice.ArgumentChoice;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface ErisCommandArgument<T> {

    T getCurrentExecutionValue();

    void setCurrentExecutionValue(T currentExecutionValue);

    String getName();

    ErisCommand getParentCommand();
    void setParentCommand(ErisCommand newParentCommand);

    boolean isNullable();

    boolean isForceChoice();

    ArgumentChoice getArgumentChoice();

    void resetValue();
    void setValue(CommandSender sender, String arg);

    Collection<String> retrieveChoices(CommandSender choicesRequester);

    boolean isArgumentValid(CommandSender requester, String argumentAsString);
    T convertValue(CommandSender requester, String argumentAsString);

}
