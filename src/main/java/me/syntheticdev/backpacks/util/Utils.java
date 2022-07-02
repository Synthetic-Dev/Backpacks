package me.syntheticdev.backpacks.util;

import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class Utils {
    public static String toDisplayCase(String s) {

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toTitleCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }

    @Nullable
    public static DyeColor getDyeColor(ItemStack item) {
        switch (item.getType()) {
            case WHITE_DYE: return DyeColor.WHITE;
            case LIGHT_GRAY_DYE: return DyeColor.LIGHT_GRAY;
            case GRAY_DYE: return DyeColor.GRAY;
            case BLACK_DYE: return DyeColor.BLACK;
            case YELLOW_DYE: return DyeColor.YELLOW;
            case ORANGE_DYE: return DyeColor.ORANGE;
            case RED_DYE: return DyeColor.RED;
            case BROWN_DYE: return DyeColor.BROWN;
            case LIME_DYE: return DyeColor.LIME;
            case GREEN_DYE: return DyeColor.GREEN;
            case LIGHT_BLUE_DYE: return DyeColor.LIGHT_BLUE;
            case CYAN_DYE: return DyeColor.CYAN;
            case BLUE_DYE: return DyeColor.BLUE;
            case PINK_DYE: return DyeColor.PINK;
            case MAGENTA_DYE: return DyeColor.MAGENTA;
            case PURPLE_DYE: return DyeColor.PURPLE;
            default: return null;
        }
    }
}