package me.syntheticdev.backpacks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Recipes {

    public static void register() {
        {
            NamespacedKey recipeKey = new NamespacedKey(Backpacks.getPlugin(), "leather_bound");
            ShapedRecipe recipe = new ShapedRecipe(recipeKey, Items.LEATHER_BOUND.create());
            recipe.shape(
                    "SSS",
                    "L L",
                    "SSS"
            );
            recipe.setIngredient('S', Material.STRING);
            recipe.setIngredient('L', Material.LEATHER);
            Bukkit.addRecipe(recipe);
        }
        {
            NamespacedKey recipeKey = new NamespacedKey(Backpacks.getPlugin(), "leather_tanned");
            FurnaceRecipe recipe = new FurnaceRecipe(recipeKey, Items.LEATHER_TANNED.create(), new RecipeChoice.ExactChoice(Items.LEATHER_BOUND.create()), 0.3f, 200);
            Bukkit.addRecipe(recipe);
        }
        {
            NamespacedKey recipeKey = new NamespacedKey(Backpacks.getPlugin(), "backpack");
            ShapedRecipe recipe = new ShapedRecipe(recipeKey, Items.BACKPACK.create());
            recipe.shape(
                    "LIL",
                    "LLL",
                    "SWS"
            );
            recipe.setIngredient('S', Material.STRING);
            recipe.setIngredient('I', Material.IRON_BLOCK);
            recipe.setIngredient('W', new RecipeChoice.MaterialChoice(Tag.WOOL));
            recipe.setIngredient('L', new RecipeChoice.ExactChoice(Items.LEATHER_TANNED.create()));
            Bukkit.addRecipe(recipe);
        }
    }
}
