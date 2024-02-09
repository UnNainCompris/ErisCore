package fr.eris.eriscore.manager.inventory.inventory;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ErisInventoryHolder implements InventoryHolder {

    @Getter
    private final ErisInventory attachInventory;

    public ErisInventoryHolder(ErisInventory attachInventory) {
        this.attachInventory = attachInventory;
    }

    public Inventory getInventory() {
        return attachInventory.getInventory();
    }

    public boolean equals(ErisInventory otherInventory) {
        return otherInventory.equals(attachInventory);
    }
}
