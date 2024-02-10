package fr.eris.eriscore.manager.command.commands;

import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.command.object.annotation.ErisExecutionParameter;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TestCommand extends IErisCommand {

    public TestCommand() {
        super("eristest", AvailableSender.CONSOLE_AND_PLAYER,
                "", Arrays.asList("erist", "etest"));
    }

    public void registerSubCommand() {

    }

    @ErisExecutionParameter(executionIdentifier = "print", requiredArgsAsName = {"testArgument1"})
    public void execute(CommandSender sender, String executionIdentifier) {
        sender.sendMessage("Execute");
    }

    @Override
    public void handleError() {

    }
}
