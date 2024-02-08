package fr.eris.eriscore.manager.inventory.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ErisInventoryHolder implements InventoryHolder {

    private final ErisInventory attachInventory;

    public ErisInventoryHolder(ErisInventory attachInventory) {
        this.attachInventory = attachInventory;
    }

    public Inventory getInventory() {
        return null;
    }
}
