package fr.eris.eriscore.api.manager.commands.object.argument.choice;

import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface ArgumentChoice {
    Collection<String> retrieveChoices(CommandSender requester);
}
