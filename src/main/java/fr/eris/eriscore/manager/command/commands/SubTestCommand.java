package fr.eris.eriscore.manager.command.commands;

import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.command.object.arguments.StringCommandArgument;
import fr.eris.eriscore.manager.command.object.error.ExecutionError;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class SubTestCommand extends IErisCommand {
    public SubTestCommand() {
        super("subtest", AvailableSender.CONSOLE_AND_PLAYER, "", Arrays.asList("stest", "subt"));
    }

    public void registerSubCommand() {

    }

    public void registerCommandArgument() {
        addCommandArgument(new StringCommandArgument("testArgument", false,
                (sender) -> Arrays.asList("Test", "est", "Banane", "Chapeaux", "Chat", "Champi")));
        addCommandArgument(new StringCommandArgument("JambonArgs", false,
                (sender) -> Arrays.asList("a", "bb", "ccc", "dddd", "eeeee", "ffffff")));
        addCommandArgument(new StringCommandArgument("HamArgs", true,
                (sender) -> Arrays.asList("HAM", "BURGER")));
    }

    public void execute(CommandSender sender) {
        sender.sendMessage("Execute SUB -A- " + retrieveArgumentValue(StringCommandArgument.class, "testArgument"));
        sender.sendMessage("Execute SUB -A- " + retrieveArgumentValue(StringCommandArgument.class, "JambonArgs"));
        sender.sendMessage("Execute SUB -A- " + retrieveArgumentValue(StringCommandArgument.class, "HamArgs"));
    }

    public void handleError(CommandSender sender, ExecutionError error, String[] args) {

    }
}
