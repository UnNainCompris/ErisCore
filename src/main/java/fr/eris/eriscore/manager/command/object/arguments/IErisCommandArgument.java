package fr.eris.eriscore.manager.command.object.arguments;

import fr.eris.eriscore.manager.command.object.IErisCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class IErisCommandArgument<T> {
    @Getter private final String name;
    @Getter @Setter private IErisCommand parentCommand;
    @Getter private final boolean canBeNull;
    private final ChoiceRetriever choiceRetriever;
    protected boolean isForceChoice;

    public IErisCommandArgument(String name, boolean canBeNull, ChoiceRetriever choiceRetriever) {
        this.name = name;
        this.canBeNull = canBeNull;
        this.choiceRetriever = choiceRetriever;
    }

    public abstract boolean isValid(CommandSender sender, String args);
    public abstract T retrieveValue(CommandSender sender, String args);

    public final List<String> getChoices(CommandSender sender) {
        if(choiceRetriever == null) return Collections.emptyList();
        List<String> choice = choiceRetriever.retrieveChoices(sender);
        return choice != null ? choice : Collections.emptyList();
    }

    public interface ChoiceRetriever {
        List<String> retrieveChoices(CommandSender sender);
    }
}
