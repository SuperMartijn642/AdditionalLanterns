package com.supermartijn642.additionallanterns;

import net.minecraft.world.item.DyeColor;

import java.util.Locale;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternColor {

    WHITE(DyeColor.WHITE, "White"),
    ORANGE(DyeColor.ORANGE, "Orange"),
    MAGENTA(DyeColor.MAGENTA, "Magenta"),
    LIGHT_BLUE(DyeColor.LIGHT_BLUE, "Light Blue"),
    YELLOW(DyeColor.YELLOW, "Yellow"),
    LIME(DyeColor.LIME, "Lime"),
    PINK(DyeColor.PINK, "Pink"),
    GRAY(DyeColor.GRAY, "Gray"),
    LIGHT_GRAY(DyeColor.LIGHT_GRAY, "Light Gray"),
    CYAN(DyeColor.CYAN, "Cyan"),
    PURPLE(DyeColor.PURPLE, "Purple"),
    BLUE(DyeColor.BLUE, "Blue"),
    BROWN(DyeColor.BROWN, "Brown"),
    GREEN(DyeColor.GREEN, "Green"),
    RED(DyeColor.RED, "Red"),
    BLACK(DyeColor.BLACK, "Black");

    public final DyeColor dyeColor;
    public final String englishTranslation;

    LanternColor(DyeColor dyeColor, String englishTranslation){
        this.dyeColor = dyeColor;
        this.englishTranslation = englishTranslation;
    }

    public String getSuffix(){
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static LanternColor fromDyeColor(DyeColor dyeColor){
        for(LanternColor lanternColor : values())
            if(lanternColor.dyeColor == dyeColor)
                return lanternColor;
        return null;
    }
}
