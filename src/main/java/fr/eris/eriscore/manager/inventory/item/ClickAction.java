package fr.eris.eriscore.manager.inventory.item;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventoryHolder;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction {

    record ClickActionData(InventoryClickEvent event, ErisInventory inventory, ErisInventoryHolder inventoryHolder, ErisInventoryItem clickedItem) {
    }

    void onClick(ClickActionData data);
}
