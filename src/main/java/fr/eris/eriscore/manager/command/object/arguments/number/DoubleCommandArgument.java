package fr.eris.eriscore.manager.command.object.arguments.number;

import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import org.bukkit.command.CommandSender;

public class DoubleCommandArgument extends IErisCommandArgument<Double> {

    public DoubleCommandArgument(String name, boolean canBeNull, ChoiceRetriever choiceRetriever) {
        super(name, canBeNull, choiceRetriever);
    }

    public DoubleCommandArgument(String name, boolean canBeNull) {
        this(name, canBeNull, null);
    }

    public DoubleCommandArgument(String name) {
        this(name, false);
    }

    public boolean isValid(CommandSender sender, String args) {
        return (!isForceChoice || getChoices(sender).contains(args)) && isDouble(args);
    }

    public Double retrieveValue(CommandSender sender, String args) {
        return Double.parseDouble(args);
    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
