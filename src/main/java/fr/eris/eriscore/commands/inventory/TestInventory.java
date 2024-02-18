package fr.eris.eriscore.commands.inventory;

import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
import fr.eris.eriscore.utils.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class TestInventory extends ErisAnimatedInventory {

    private final Random random;

    public TestInventory(Player target) {
        super(target, "TestInventory", 6);
        random = new Random();
    }

    public void loadContent() {
        //for(int i = 0 ; i < getInventoryRowAmount() * 9 ; i++) {
        //    Material randomMaterial = Material.values()[random.nextInt(Material.values().length - 1)];
        //    setItem(i, (sender) -> ItemBuilder.create().setMaterial(randomMaterial).setAmount(random.nextInt(50) + 1).build(),
        //            (data) -> data.getEvent().getWhoClicked().sendMessage(data.getEvent().getCurrentItem().toString()));
        //}
    }

    public void onClicked(InventoryClickEvent event) {

    }
}
