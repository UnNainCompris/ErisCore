package fr.eris.eriscore.manager.command.object.arguments.number;

import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.commands.object.argument.choice.ArgumentChoice;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class BigDecimalCommandArgument extends IErisCommandArgument<BigDecimal> {

    public BigDecimalCommandArgument(String name, boolean nullable, boolean isForceChoice, ArgumentChoice argumentChoice) {
        super(name, nullable, isForceChoice, argumentChoice);
    }

    public BigDecimalCommandArgument(String name, boolean canBeNull) {
        super(name, canBeNull, false, null);
    }

    public BigDecimalCommandArgument(String name) {
        super(name, false, false, null);
    }

    public boolean isArgumentValid(CommandSender sender, String args) {
        return (!isForceChoice() || retrieveChoices(sender).contains(args)) && isBigDecimal(args);
    }

    public boolean isBigDecimal(String args) {
        try {
            new BigDecimal(args);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public BigDecimal convertValue(CommandSender sender, String args) {
        return new BigDecimal(args);
    }
}
