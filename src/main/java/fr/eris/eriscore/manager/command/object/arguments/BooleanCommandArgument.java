package fr.eris.eriscore.manager.command.object.arguments;

import fr.eris.eriscore.manager.commands.object.argument.choice.ArgumentChoice;
import fr.eris.eriscore.utils.list.ListUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BooleanCommandArgument extends IErisCommandArgument<Boolean> {

    private final List<String> trueValue = Arrays.asList("true", "yes", "enabled");
    private final List<String> falseValue = Arrays.asList("false", "no", "disable");
    private final List<String> booleanChoices = ListUtils.concatList(falseValue, trueValue);

    public BooleanCommandArgument(String name, boolean canBeNull, boolean isForceChoice, ArgumentChoice argumentChoice) {
        super(name, canBeNull, isForceChoice, argumentChoice);
        if(argumentChoice == null) setArgumentChoice((sender) -> booleanChoices);
    }

    public BooleanCommandArgument(String name, boolean canBeNull) {
        this(name, canBeNull, false, null);
    }

    public BooleanCommandArgument(String name) {
        this(name, false);
    }

    public boolean isArgumentValid(CommandSender sender, String args) {
        return !isForceChoice() || retrieveChoices(sender).contains(args);
    }

    public Boolean convertValue(CommandSender sender, String args) {
        return Boolean.parseBoolean(args);
    }
}
