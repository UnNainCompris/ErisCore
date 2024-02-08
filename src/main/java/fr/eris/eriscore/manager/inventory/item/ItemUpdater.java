package fr.eris.eriscore.manager.inventory.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemUpdater {
    ItemStack getItem(Player requester);
}
