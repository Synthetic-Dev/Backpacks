package me.syntheticdev.backpacks.items.Backpack;

import org.bukkit.DyeColor;

import javax.annotation.Nullable;
import java.util.HashMap;

public enum BackpackModelData {
    DEFAULT(539101, 539102, null),
    WHITE(539111, 539112, DyeColor.WHITE),
    LIGHT_GRAY(539121, 539122, DyeColor.LIGHT_GRAY),
    GRAY(539131, 539132, DyeColor.GRAY),
    BLACK(539141, 539142, DyeColor.BLACK),
    YELLOW(539151, 539152, DyeColor.YELLOW),
    ORANGE(539161, 539162, DyeColor.ORANGE),
    RED(539171, 539172, DyeColor.RED),
    BROWN(539181, 539182, DyeColor.BROWN),
    LIME(539191, 539192, DyeColor.LIME),
    GREEN(539201, 539202, DyeColor.GREEN),
    LIGHT_BLUE(539211, 539212, DyeColor.LIGHT_BLUE),
    CYAN(539221, 539222, DyeColor.CYAN),
    BLUE(539231, 539232, DyeColor.BLUE),
    PINK(539241, 539242, DyeColor.PINK),
    MAGENTA(539251, 539252, DyeColor.MAGENTA),
    PURPLE(539261, 539262, DyeColor.PURPLE);

    private final int closedModel;
    private final int openedModel;
    @Nullable
    private final DyeColor color;

    private static final HashMap<DyeColor, BackpackModelData> dyeColorToModelData = new HashMap<>(values().length, 1);

    static {
        for (BackpackModelData data : values()) {
            if (data.color == null) continue;
            dyeColorToModelData.put(data.color, data);
        }
    }

    BackpackModelData(final int closedModel, final int openedModel, @Nullable DyeColor color) {
        this.closedModel = closedModel;
        this.openedModel = openedModel;
        this.color = color;
    }

    public boolean isColored() { return this.color != null; }
    public DyeColor getColor() { return this.color; }
    public int getClosedModelData() { return this.closedModel; }
    public int getOpenedModelData() { return this.openedModel; }

    @Nullable
    public static BackpackModelData fromColor(DyeColor color) {
        return dyeColorToModelData.get(color);
    }
}
