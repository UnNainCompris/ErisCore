package fr.eris.eriscore.manager.inventory.item;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventoryHolder;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction {

    @Getter
    class ClickActionData {
        private final InventoryClickEvent event;
        private final ErisInventory inventory;
        private final ErisInventoryHolder inventoryHolder;
        private final ErisInventoryItem clickedItem;

        public ClickActionData(InventoryClickEvent event, ErisInventory inventory,
                               ErisInventoryHolder inventoryHolder, ErisInventoryItem clickedItem) {
            this.event = event;
            this.inventory = inventory;
            this.inventoryHolder = inventoryHolder;
            this.clickedItem = clickedItem;
        }
    }

    void onClick(ClickActionData data);
}
