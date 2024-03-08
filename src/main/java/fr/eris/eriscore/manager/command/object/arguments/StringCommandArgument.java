package fr.eris.eriscore.manager.command.object.arguments;

import fr.eris.eriscore.manager.commands.object.argument.ErisCommandArgument;
import fr.eris.eriscore.manager.commands.object.argument.choice.ArgumentChoice;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StringCommandArgument extends IErisCommandArgument<String> {

    public StringCommandArgument(String name, boolean nullable, boolean isForceChoice, ArgumentChoice argumentChoice) {
        super(name, nullable, isForceChoice, argumentChoice);
    }

    public StringCommandArgument(String name, boolean canBeNull) {
        super(name, canBeNull, false, null);
    }

    public StringCommandArgument(String name) {
        super(name, false, false, null);
    }

    public boolean isArgumentValid(CommandSender sender, String args) {
        return !isForceChoice() || retrieveChoices(sender).contains(args);
    }

    public String convertValue(CommandSender sender, String args) {
        return args;
    }
}
