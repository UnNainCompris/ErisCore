package fr.eris.eriscore.manager.command.object.arguments;

import org.bukkit.command.CommandSender;

import java.util.List;

public class StringCommandArgument extends IErisCommandArgument<String> {

    public StringCommandArgument(String name, boolean canBeNull, ChoiceRetriever choiceRetriever) {
        super(name, canBeNull, choiceRetriever);
    }

    public StringCommandArgument(String name, boolean canBeNull) {
        this(name, canBeNull, null);
    }

    public StringCommandArgument(String name) {
        this(name, false);
    }

    public boolean isValid(CommandSender sender, String args) {
        return !isForceChoice || getChoices(sender).contains(args);
    }

    public String retrieveValue(CommandSender sender, String args) {
        return args;
    }
}
