package me.syntheticdev.backpacks.items;

import me.syntheticdev.backpacks.Items;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class TannedLeather extends Item implements Listener {
    public TannedLeather() {
        super(Material.LEATHER, "Tanned Leather", 539002);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        Recipe recipe = event.getRecipe();

        if (recipe == null || !Items.BACKPACK.is(recipe.getResult())) {
            for (ItemStack item : inventory.getMatrix()) {
                if (item == null || item.getType().equals(Material.AIR)) continue;
                if (this.is(item)) {
                    inventory.setResult(null);
                    return;
                }
            }
            return;
        }

        //Makes a unique backpack
        inventory.setResult(Items.BACKPACK.create());
    }
}
