package fr.eris.eriscore.manager.command.object.arguments.number;

import fr.eris.eriscore.manager.command.object.arguments.IErisCommandArgument;
import fr.eris.eriscore.manager.commands.object.argument.choice.ArgumentChoice;
import org.bukkit.command.CommandSender;

import java.math.BigInteger;

public class BigIntegerCommandArgument extends IErisCommandArgument<BigInteger> {

    public BigIntegerCommandArgument(String name, boolean nullable, boolean isForceChoice, ArgumentChoice argumentChoice) {
        super(name, nullable, isForceChoice, argumentChoice);
    }

    public BigIntegerCommandArgument(String name, boolean canBeNull) {
        super(name, canBeNull, false, null);
    }

    public BigIntegerCommandArgument(String name) {
        super(name, false, false, null);
    }

    public boolean isArgumentValid(CommandSender sender, String args) {
        return (!isForceChoice() || retrieveChoices(sender).contains(args)) && isBigInteger(args);
    }

    public boolean isBigInteger(String args) {
        try {
            new BigInteger(args);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public BigInteger convertValue(CommandSender sender, String args) {
        return new BigInteger(args);
    }
}
