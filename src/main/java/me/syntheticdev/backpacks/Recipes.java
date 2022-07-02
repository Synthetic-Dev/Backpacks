package me.syntheticdev.backpacks;

import org.bukkit.*;
import org.bukkit.inventory.*;

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
        {
            NamespacedKey recipeKey = new NamespacedKey(Backpacks.getPlugin(), "backpack_dye");
            ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, Items.BACKPACK.create());
            recipe.addIngredient(new RecipeChoice.MaterialChoice(
                    Material.WHITE_DYE,
                    Material.LIGHT_GRAY_DYE,
                    Material.GRAY_DYE,
                    Material.BLACK_DYE,
                    Material.YELLOW_DYE,
                    Material.ORANGE_DYE,
                    Material.RED_DYE,
                    Material.BROWN_DYE,
                    Material.LIME_DYE,
                    Material.GREEN_DYE,
                    Material.LIGHT_BLUE_DYE,
                    Material.CYAN_DYE,
                    Material.BLUE_DYE,
                    Material.PINK_DYE,
                    Material.MAGENTA_DYE,
                    Material.PURPLE_DYE
            ));
            recipe.addIngredient(Material.FEATHER);
            recipe.setGroup("");
            Bukkit.addRecipe(recipe);
        }
    }
}
