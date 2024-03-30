package fr.eris.eriscore.manager.inventory;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private static List<ErisInventory> erisInventoryList;

    public void start() {
        erisInventoryList = new ArrayList<>();
    }

    public void stop() {
        for(ErisInventory erisInventory : erisInventoryList) {
            erisInventory.destroy();
        }
    }

    public static void appendNewInventory(ErisInventory newInventory) {
        erisInventoryList.add(newInventory);
    }
}
