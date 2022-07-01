package me.syntheticdev.backpacks.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class InventorySerializer {

    public static String serialize(Inventory inventory) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

        dataOutput.writeInt(inventory.getSize());

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType().equals(Material.AIR)) {
                dataOutput.writeObject("");
            } else {
                dataOutput.writeObject(item);
            }
        }

        dataOutput.close();
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }

    public static Inventory deserialize(String base64, @Nullable String title) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

        Inventory inventory;
        if (title != null) inventory = Bukkit.createInventory(null, dataInput.readInt(), title);
        else inventory = Bukkit.createInventory(null, dataInput.readInt());

        for (int i = 0; i < inventory.getSize(); i++) {
            Object item = dataInput.readObject();
            if (item == "") continue;
            inventory.setItem(i, (ItemStack)item);
        }

        dataInput.close();
        return inventory;
    }
}
