package me.syntheticdev.backpacks.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    private Material base;
    private String name;
    private int customModelData;

    public Item(Material base, String name, int customModelData) {
        this.base = base;
        this.name = name;
        this.customModelData = customModelData;
    }

    public Material getType() {
        return this.base;
    }
    public String getName() { return this.name; }

    public boolean is(ItemStack item) {
        if (!item.getType().equals(this.base) || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getCustomModelData() == this.customModelData;
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(this.base);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + this.name);
        meta.setCustomModelData(this.customModelData);
        item.setItemMeta(meta);
        return item;
    }
}
