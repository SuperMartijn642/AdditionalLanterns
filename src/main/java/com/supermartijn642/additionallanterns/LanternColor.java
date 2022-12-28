package com.supermartijn642.additionallanterns;

import net.minecraft.item.EnumDyeColor;

import java.util.Locale;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternColor {

    WHITE(EnumDyeColor.WHITE, "White"),
    ORANGE(EnumDyeColor.ORANGE, "Orange"),
    MAGENTA(EnumDyeColor.MAGENTA, "Magenta"),
    LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE, "Light Blue"),
    YELLOW(EnumDyeColor.YELLOW, "Yellow"),
    LIME(EnumDyeColor.LIME, "Lime"),
    PINK(EnumDyeColor.PINK, "Pink"),
    GRAY(EnumDyeColor.GRAY, "Gray"),
    LIGHT_GRAY(EnumDyeColor.SILVER, "Light Gray"),
    CYAN(EnumDyeColor.CYAN, "Cyan"),
    PURPLE(EnumDyeColor.PURPLE, "Purple"),
    BLUE(EnumDyeColor.BLUE, "Blue"),
    BROWN(EnumDyeColor.BROWN, "Brown"),
    GREEN(EnumDyeColor.GREEN, "Green"),
    RED(EnumDyeColor.RED, "Red"),
    BLACK(EnumDyeColor.BLACK, "Black");

    public final EnumDyeColor dyeColor;
    public final String englishTranslation;

    LanternColor(EnumDyeColor dyeColor, String englishTranslation){
        this.dyeColor = dyeColor;
        this.englishTranslation = englishTranslation;
    }

    public String getSuffix(){
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static LanternColor fromDyeColor(EnumDyeColor dyeColor){
        for(LanternColor lanternColor : values())
            if(lanternColor.dyeColor == dyeColor)
                return lanternColor;
        return null;
    }
}
