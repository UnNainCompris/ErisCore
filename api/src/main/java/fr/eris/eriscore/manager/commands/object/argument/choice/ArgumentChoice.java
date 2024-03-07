package fr.eris.eriscore.manager.commands.object.argument.choice;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;

public interface ArgumentChoice {
    Collection<String> retrieveChoices(CommandSender requester);
}
