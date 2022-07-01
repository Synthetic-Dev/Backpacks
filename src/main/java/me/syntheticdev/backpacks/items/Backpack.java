package me.syntheticdev.backpacks.items;

import me.syntheticdev.backpacks.Backpacks;
import me.syntheticdev.backpacks.util.InventorySerializer;
import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class Backpack extends Item implements Listener {
    private final HashMap<String, Inventory> inventories = new HashMap<>();

    public Backpack() {
        super(Material.FEATHER, "Backpack", 539101);
    }

    @Override
    public boolean is(ItemStack item) {
        if (!item.getType().equals(this.getType()) || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getCustomModelData() == 539101 || meta.getCustomModelData() == 539102;
    }

    @Override
    public ItemStack create() {
        ItemStack item = super.create();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer nbt = meta.getPersistentDataContainer();
        NamespacedKey identifier = new NamespacedKey(Backpacks.getPlugin(), "identifier");
        nbt.set(identifier, PersistentDataType.STRING, UUID.randomUUID().toString());
        NamespacedKey inventoryKey = new NamespacedKey(Backpacks.getPlugin(), "inventory");
        try {
            String encodedInventory = InventorySerializer.serialize(Bukkit.createInventory(null, 9));
            nbt.set(inventoryKey, PersistentDataType.STRING, encodedInventory);
        } catch (Exception err) {
            err.printStackTrace();
        }

        item.setItemMeta(meta);
        return item;
    }

    private ItemStack clone(ItemStack backpack) {
        ItemStack item = backpack.clone();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer nbt = meta.getPersistentDataContainer();
        NamespacedKey identifier = new NamespacedKey(Backpacks.getPlugin(), "identifier");
        nbt.set(identifier, PersistentDataType.STRING, UUID.randomUUID().toString());
        item.setItemMeta(meta);

        return item;
    }

    private String getIdentifier(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer nbt = meta.getPersistentDataContainer();
        NamespacedKey identifier = new NamespacedKey(Backpacks.getPlugin(), "identifier");
        return nbt.get(identifier, PersistentDataType.STRING);
    }

    @Nullable
    private String getIdentifier(Inventory inventory) {
        for (Map.Entry<String, Inventory> entry : this.inventories.entrySet()) {
            if (entry.getValue() == inventory) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Nullable
    private ItemStack getBackpack(PlayerInventory inventory, String identifier) {
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || !item.hasItemMeta() || !this.is(item)) continue;

            if (this.getIdentifier(item) == identifier) return item;
        }

        return null;
    }

    private void saveInventory(Inventory inventory, ItemStack backpack) {
        ItemMeta meta = backpack.getItemMeta();
        PersistentDataContainer nbt = meta.getPersistentDataContainer();

        NamespacedKey inventoryKey = new NamespacedKey(Backpacks.getPlugin(), "inventory");
        try {
            String encodedInventory = InventorySerializer.serialize(inventory);
            nbt.set(inventoryKey, PersistentDataType.STRING, encodedInventory);
        } catch (IOException err) {
            err.printStackTrace();
        }

        backpack.setItemMeta(meta);
    }

    public void saveAllInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = player.getInventory();
            for (Map.Entry<String, Inventory> entry : this.inventories.entrySet()) {
                ItemStack backpack = this.getBackpack(inventory, entry.getKey());
                if (backpack != null) {
                    this.closeBackpack(backpack);
                }
            }
        }
    }

    private void closeBackpack(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(539101);
        item.setItemMeta(meta);

        String identifier = this.getIdentifier(item);
        Inventory inventory = this.inventories.get(identifier);
        if (inventory == null) return;

        this.saveInventory(inventory, item);
        this.inventories.remove(identifier);

        for (HumanEntity entity : new ArrayList<>(inventory.getViewers())) {
            entity.closeInventory();
        }
    }

    @EventHandler
    public void onInventoryCreative(InventoryCreativeEvent event) {
        ItemStack item = event.getCurrentItem();
        if (event.getAction() == InventoryAction.CLONE_STACK) {
            if (item != null && this.is(item)) {
                event.setCursor(this.clone(item));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();
        Inventory bottomInventory = event.getView().getBottomInventory();

        ItemStack item = event.getCurrentItem();
        if (event.getAction() == InventoryAction.CLONE_STACK) {
            if (item != null && this.is(item)) {
                event.setCancelled(true);
            }
            return;
        }

        boolean isBackpackInventory = this.inventories.containsValue(inventory);
        if (!(isBackpackInventory || inventory.getHolder() instanceof ShulkerBox)) return;

        ItemStack cursor = event.getCursor();
        if (cursor != null && (this.is(cursor) || cursor.getType().equals(Material.SHULKER_BOX))) {
            if ((event.getAction() == InventoryAction.PLACE_ALL
                    || event.getAction() == InventoryAction.PLACE_ONE
                    || event.getAction() == InventoryAction.PLACE_SOME
                    || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
                    && event.getClickedInventory().equals(inventory)
            ) {
                event.setCancelled(true);
            }
            return;
        }

        String identifier = this.getIdentifier(inventory);

        if (item != null && (this.is(item) || item.getType().equals(Material.SHULKER_BOX))) {
            if (this.is(item) && this.getIdentifier(item) == identifier) {
                event.setCancelled(true);
            }
            if (event.isShiftClick() && event.getClickedInventory().equals(bottomInventory)) {
                event.setCancelled(true);
            }
            return;
        }

        if (!isBackpackInventory) return;

        HumanEntity player = event.getWhoClicked();
        ItemStack backpack = this.getBackpack(player.getInventory(), identifier);
        if (backpack != null) {
            this.saveInventory(inventory, backpack);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        if (!(this.inventories.containsValue(inventory) || inventory.getHolder() instanceof ShulkerBox)) return;

        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            if (this.is(entry.getValue()) || entry.getValue().getType().equals(Material.SHULKER_BOX)) {
                event.setCancelled(true);
                return;
            }
        }

        HumanEntity player = event.getWhoClicked();
        String identifier = this.getIdentifier(inventory);
        ItemStack backpack = this.getBackpack(player.getInventory(), identifier);
        if (backpack != null) {
            this.saveInventory(inventory, backpack);
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.hasItem() || !player.isSneaking()) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) return;

        ItemStack item = event.getItem();
        if (item == null || !this.is(item)) return;
        event.setUseInteractedBlock(Event.Result.DENY);

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer nbt = meta.getPersistentDataContainer();

        NamespacedKey inventoryKey = new NamespacedKey(Backpacks.getPlugin(), "inventory");
        String encodedInventory = nbt.get(inventoryKey, PersistentDataType.STRING);
        String identifier = this.getIdentifier(item);

        Inventory inventory = this.inventories.get(identifier);
        if (inventory == null) {
            try {
                inventory = InventorySerializer.deserialize(encodedInventory, meta.hasDisplayName() ? meta.getDisplayName() : "Backpack");
            } catch (Exception err) {
                err.printStackTrace();
            }

            if (inventory != null) this.inventories.put(identifier, inventory);
        }

        if (inventory != null) {
            meta.setCustomModelData(539102);
            item.setItemMeta(meta);
            player.openInventory(inventory);

            World world = player.getWorld();
            world.playSound(player, Sound.ITEM_BUNDLE_INSERT, 1f, 1f);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!this.inventories.containsValue(inventory)) return;

        HumanEntity player = event.getPlayer();
        String identifier = this.getIdentifier(inventory);
        ItemStack backpack = this.getBackpack(player.getInventory(), identifier);
        if (backpack == null) return;

        this.closeBackpack(backpack);

        World world = player.getWorld();
        world.playSound(player, Sound.ITEM_BUNDLE_REMOVE_ONE, 1f, 1f);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (!this.is(item)) return;

        this.closeBackpack(item);
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
