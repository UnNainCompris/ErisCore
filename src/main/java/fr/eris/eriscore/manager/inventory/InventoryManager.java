package fr.eris.eriscore.manager.inventory;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.utils.Manager;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager extends Manager<ErisCore> {

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
