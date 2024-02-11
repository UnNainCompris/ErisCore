package fr.eris.eriscore.manager.command.commands;

import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.command.object.arguments.StringCommandArgument;
import fr.eris.eriscore.manager.command.object.error.ExecutionError;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestCommand extends IErisCommand {

    public TestCommand() {
        super("eristest", AvailableSender.CONSOLE_AND_PLAYER,
                "", Arrays.asList("erist", "etest"));
        registerCommand();
    }

    public void registerSubCommand() {
        addSubcommand(new SubTestCommand());
    }

    public void registerCommandArgument() {
        addCommandArgument(new StringCommandArgument("testArgument", false, (sender) -> Collections.emptyList()));
    }

    public void execute(CommandSender sender) {
        sender.sendMessage("Execute");
    }

    public void handleError(CommandSender sender, ExecutionError error, String[] args) {

    }
}
