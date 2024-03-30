package fr.eris.eriscore.manager.command.object.arguments;

import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.commands.object.argument.ErisCommandArgument;
import fr.eris.eriscore.api.manager.commands.object.argument.choice.ArgumentChoice;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;

@Getter
public abstract class IErisCommandArgument<T> implements ErisCommandArgument<T> {

    @Setter private T currentExecutionValue;

    private final String name;
    @Setter private ErisCommand parentCommand;

    private final boolean nullable; // a nullable argument should be added at the end !
    private final boolean forceChoice;
    @Setter private ArgumentChoice argumentChoice;

    public IErisCommandArgument(String name, boolean nullable, boolean isForceChoice, ArgumentChoice argumentChoice) {
        this.name = name;
        this.nullable = nullable;
        this.argumentChoice = argumentChoice;
        this.forceChoice = isForceChoice;
    }

    public abstract boolean isArgumentValid(CommandSender sender, String args);
    public abstract T convertValue(CommandSender sender, String args);

    public final Collection<String> retrieveChoices(CommandSender sender) {
        if(argumentChoice == null) return Collections.emptyList();
        Collection<String> choice = argumentChoice.retrieveChoices(sender);
        return choice != null ? choice : Collections.emptyList();
    }

    public void setValue(CommandSender sender, String arg) {
        if(arg == null) {
            currentExecutionValue = null;
            return;
        }
        currentExecutionValue = convertValue(sender, arg);
    }

    public void resetValue() {
        currentExecutionValue = null;
    }
}
