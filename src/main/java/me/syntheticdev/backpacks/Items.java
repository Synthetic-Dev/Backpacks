package me.syntheticdev.backpacks;

import me.syntheticdev.backpacks.items.Backpack;
import me.syntheticdev.backpacks.items.BoundLeather;
import me.syntheticdev.backpacks.items.TannedLeather;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Items {
    public static final BoundLeather LEATHER_BOUND = new BoundLeather();
    public static final TannedLeather LEATHER_TANNED = new TannedLeather();
    public static final Backpack BACKPACK = new Backpack();

    public static void registerEvents(PluginManager manager, JavaPlugin plugin) {
        manager.registerEvents(LEATHER_BOUND, plugin);
        manager.registerEvents(LEATHER_TANNED, plugin);
        manager.registerEvents(BACKPACK, plugin);
    }
}
