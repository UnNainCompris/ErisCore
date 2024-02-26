package fr.eris.eriscore.manager.command.object.arguments.number;

import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import org.bukkit.command.CommandSender;

public class LongCommandArgument extends IErisCommandArgument<Long> {

    public LongCommandArgument(String name, boolean canBeNull, ChoiceRetriever choiceRetriever) {
        super(name, canBeNull, choiceRetriever);
    }

    public LongCommandArgument(String name, boolean canBeNull) {
        this(name, canBeNull, null);
    }

    public LongCommandArgument(String name) {
        this(name, false);
    }

    public boolean isValid(CommandSender sender, String args) {
        return (!isForceChoice || getChoices(sender).contains(args)) && isLong(args);
    }

    public Long retrieveValue(CommandSender sender, String args) {
        return Long.parseLong(args);
    }

    public boolean isLong(String input) {
        try {
            Long.parseLong(input);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
