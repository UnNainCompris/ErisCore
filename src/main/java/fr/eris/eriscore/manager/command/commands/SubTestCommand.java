package fr.eris.eriscore.manager.command.commands;

import fr.eris.eriscore.manager.command.object.IErisCommand;
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

    }

    public void execute(CommandSender sender) {
        sender.sendMessage("Execute sub");
    }

    public void handleError(CommandSender sender, ExecutionError error, String[] args) {

    }
}
