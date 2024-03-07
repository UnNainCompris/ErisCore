package fr.eris.eriscore.manager.commands.object.argument;

import fr.eris.eriscore.manager.commands.object.ErisCommand;
import fr.eris.eriscore.manager.commands.object.argument.choice.ArgumentChoice;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface ErisCommandArgument<T> {

    T getCurrentExecutionValue();

    String getName();

    ErisCommand getParentCommand();
    void setParentCommand(ErisCommand newParentCommand);

    boolean isNullable();

    boolean isForceChoice();

    ArgumentChoice getArgumentChoice();

    void resetValue();
    void setCurrentExecutionValue(CommandSender requester, String argumentAsString);

    Collection<String> retrieveChoices(CommandSender choicesRequester);

    boolean isArgumentValid(CommandSender requester, String argumentAsString);
    T convertValue(CommandSender requester, String argumentAsString);

}
