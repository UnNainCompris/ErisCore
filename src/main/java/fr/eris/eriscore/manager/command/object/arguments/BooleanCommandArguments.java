package fr.eris.eriscore.manager.command.object.arguments;

import fr.eris.eriscore.utils.list.ListUtils;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BooleanCommandArguments extends IErisCommandArgument<Boolean> {

    private final List<String> trueValue = Arrays.asList("true", "yes", "enabled");
    private final List<String> falseValue = Arrays.asList("false", "no", "disable");
    private final List<String> booleanChoices = ListUtils.concatList(falseValue, trueValue);

    public BooleanCommandArguments(String name, boolean canBeNull, ChoiceRetriever choiceRetriever) {
        super(name, canBeNull, choiceRetriever);
        if(choiceRetriever == null) this.choiceRetriever = (sender) -> booleanChoices;
    }

    public BooleanCommandArguments(String name, boolean canBeNull) {
        this(name, canBeNull, null);
    }

    public BooleanCommandArguments(String name) {
        this(name, false);
    }

    public boolean isValid(CommandSender sender, String args) {
        return !isForceChoice || getChoices(sender).contains(args);
    }

    public Boolean retrieveValue(CommandSender sender, String args) {
        return Boolean.parseBoolean(args);
    }
}
