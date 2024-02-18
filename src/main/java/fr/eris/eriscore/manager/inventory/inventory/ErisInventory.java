package fr.eris.eriscore.manager.inventory.inventory;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.inventory.item.ClickAction;
import fr.eris.eriscore.manager.inventory.item.ErisInventoryItem;
import fr.eris.eriscore.manager.inventory.item.ItemUpdater;
import fr.eris.eriscore.utils.bukkit.color.ColorUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
    1 inventory 1 player
 */
public abstract class ErisInventory implements Listener {

    @Getter protected Inventory inventory;
    protected ErisInventoryHolder inventoryHolder;
    protected Player viewers;
    @Getter private int inventoryRowAmount;
    private String inventoryName;
    private final HashMap<Integer, ErisInventoryItem> inventoryContent;
    private final UUID activeId;
    private boolean destroying = false;

    public ErisInventory(Player target, String inventoryName, int inventoryRowAmount) {
        this(target, inventoryName, inventoryRowAmount, null);
    }

    public ErisInventory(Player target, String inventoryName, int inventoryRowAmount,
                         ErisInventoryHolder inventoryHolder) {
        this.activeId = UUID.randomUUID();
        this.inventoryContent = new HashMap<>();
        if(inventoryRowAmount <= 0 || inventoryRowAmount > 6)
            throw new IllegalArgumentException("Inventory row amount is out of bounds [1;6]");
        this.inventoryRowAmount = inventoryRowAmount;
        this.inventoryName = inventoryName;
        this.viewers = target;
        this.inventoryHolder = inventoryHolder == null ? new ErisInventoryHolder(this) : inventoryHolder;
        Bukkit.getServer().getPluginManager().registerEvents(this, ErisCore.getInstance());
    }

    public void openInventory() {
        updateInventory();
        viewers.openInventory(inventory);
    }

    public void closeInventory() {
        if(!isSimilar(viewers.getOpenInventory().getTopInventory())) return;
        viewers.closeInventory();
    }

    public void createInventory() {
        closeInventory();
        this.inventory = Bukkit.createInventory(inventoryHolder,
                getRealInventorySize(), ColorUtils.translateColor(inventoryName));
    }

    public boolean shouldCreateNewInventory() {
        return inventory == null || inventory.getSize() != getRealInventorySize() ||
                inventory.getHolder() == null;
    }

    private int getRealInventorySize() {
        return (inventoryRowAmount * 9);
    }

    public void updateInventory() {
        if(shouldCreateNewInventory())
            createInventory();
        if(inventory == null)
            return;
        loadContent();
        for(Map.Entry<Integer, ErisInventoryItem> slotItem : inventoryContent.entrySet()) {
            inventory.setItem(slotItem.getKey(), slotItem.getValue().getItemUpdater().getItem(viewers));
        }
    }

    public boolean equals(Object other) {
        return other instanceof ErisInventory &&
            ((ErisInventory) other).activeId.equals(this.activeId);
    }

    public abstract void loadContent();

    public void setItem(int targetSlot, ErisInventoryItem erisItem) {
        if(targetSlot < 0) throw new IllegalArgumentException("Slot is out of bounds of the inventory " +
                "[" + 0 + ";" + (inventoryRowAmount * 9 - 1) + "]");
        inventoryContent.put(targetSlot, erisItem);
    }

    public void setItem(int targetSlot, ItemUpdater itemUpdater) {
        setItem(targetSlot, ErisInventoryItem.create(itemUpdater));
    }

    public void setItem(int targetSlot, ItemUpdater itemUpdater, ClickAction clickAction) {
        setItem(targetSlot, ErisInventoryItem.create(itemUpdater, clickAction));
    }

    public boolean isSimilar(Inventory otherInventory) {
        return otherInventory != null && otherInventory.getHolder() instanceof ErisInventoryHolder &&
                ((ErisInventoryHolder) otherInventory.getHolder()).equals(this);
    }

    public void destroy() {
        destroying = true;
        viewers.closeInventory();
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!isSimilar(event.getInventory())) return;
        int clickedSlot = event.getSlot();
        if(inventoryContent.containsKey(clickedSlot)) {
            ErisInventoryItem item = inventoryContent.get(clickedSlot);
            if(item.getClickAction() != null)
                item.getClickAction().onClick(new ClickAction.ClickActionData(event, this, inventoryHolder, item));
        }
        onClicked(event);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if(!isSimilar(event.getInventory())) return;
        if(!destroying)
            onClosed(event);
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        if(!isSimilar(event.getInventory())) return;
        if(!destroying)
            onOpened(event);
    }

    public abstract void onClicked(InventoryClickEvent event);
    public void onClosed(InventoryCloseEvent event){}
    public void onOpened(InventoryOpenEvent event){}
}
