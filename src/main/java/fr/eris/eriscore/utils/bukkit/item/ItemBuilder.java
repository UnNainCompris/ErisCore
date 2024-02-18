package fr.eris.eriscore.utils.bukkit.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {

    private ItemStack itemStack;


    public static ItemBuilder create(ItemStack fromItem) {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.itemStack = fromItem;
        return itemBuilder;
    }

    public static ItemBuilder air() {
        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.itemStack = new ItemStack(Material.AIR);
        return itemBuilder;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
}
