package me.syntheticdev.backpacks.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class BoundLeather extends Item implements Listener {
    public BoundLeather() {
        super(Material.LEATHER, "Bound Leather", 539001);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();

        for (ItemStack item : inventory.getMatrix()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            if (this.is(item)) {
                inventory.setResult(null);
                return;
            }
        }
    }
}
