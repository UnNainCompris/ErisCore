package fr.eris.eriscore.manager.inventory.item;

import lombok.Getter;

@Getter
public class ErisInventoryItem {

    private final ClickAction clickAction;
    private final ItemUpdater itemUpdater;
    public ErisInventoryItem(ItemUpdater itemUpdater, ClickAction clickAction) {
        if(itemUpdater == null)
            throw new IllegalArgumentException("ItemUpdate cannot be null");
        this.itemUpdater = itemUpdater;
        this.clickAction = clickAction;
    }

    public static ErisInventoryItem create(ItemUpdater itemUpdater) {
        return new ErisInventoryItem(itemUpdater, null);
    }

    public static ErisInventoryItem create(ItemUpdater itemUpdater, ClickAction clickAction) {
        return new ErisInventoryItem(itemUpdater, clickAction);
    }

}
