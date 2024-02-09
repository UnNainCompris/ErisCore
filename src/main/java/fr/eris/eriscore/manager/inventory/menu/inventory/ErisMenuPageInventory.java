package fr.eris.eriscore.manager.inventory.menu.inventory;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventoryHolder;
import fr.eris.eriscore.manager.inventory.menu.object.ErisMenu;
import org.bukkit.entity.Player;

public abstract class ErisMenuPageInventory extends ErisInventory {

    private final String menuName;
    private final ErisMenuPageInventory parentInventory;
    private final ErisMenu parentMenu;

    public ErisMenuPageInventory(Player target, String inventoryName, int inventoryRowAmount,
                                 ErisMenuPageInventory parentInventory, ErisMenu parentMenu,
                                 String menuName) {
        this(target, inventoryName, inventoryRowAmount, null, menuName,
                parentInventory, parentMenu);
    }

    public ErisMenuPageInventory(Player target, String inventoryName, int inventoryRowAmount,
                                 ErisInventoryHolder inventoryHolder, String menuName,
                                 ErisMenuPageInventory parentInventory, ErisMenu parentMenu) {
        super(target, inventoryName, inventoryRowAmount, inventoryHolder);
        this.menuName = menuName;
        this.parentInventory = parentInventory;
        this.parentMenu = parentMenu;
    }
}
