package me.syntheticdev.backpacks;

import com.github.syntheticdev.resourcemanager.ResourceManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Backpacks extends JavaPlugin {
    private static Backpacks plugin;

    public static Backpacks getPlugin() {
        return plugin;
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();

        Items.registerEvents(manager, this);
    }

    @Override
    public void onLoad() {
        ResourceManager manager = (ResourceManager)Bukkit.getPluginManager().getPlugin("ResourceManager");
        manager.addResource("backpacks");
    }

    @Override
    public void onEnable() {
        plugin = this;

        this.registerEvents();
        Recipes.register();
    }

    @Override
    public void onDisable() {
        Items.BACKPACK.saveAllInventories();
    }
}
