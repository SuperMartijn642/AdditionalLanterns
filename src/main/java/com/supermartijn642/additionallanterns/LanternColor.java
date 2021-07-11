package com.supermartijn642.additionallanterns;

import net.minecraft.item.EnumDyeColor;

import java.util.Locale;

/**
 * Created 7/5/2021 by SuperMartijn642
 */
public enum LanternColor {

    WHITE(EnumDyeColor.WHITE),
    ORANGE(EnumDyeColor.ORANGE),
    MAGENTA(EnumDyeColor.MAGENTA),
    LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE),
    YELLOW(EnumDyeColor.YELLOW),
    LIME(EnumDyeColor.LIME),
    PINK(EnumDyeColor.PINK),
    GRAY(EnumDyeColor.GRAY),
    LIGHT_GRAY(EnumDyeColor.SILVER),
    CYAN(EnumDyeColor.CYAN),
    PURPLE(EnumDyeColor.PURPLE),
    BLUE(EnumDyeColor.BLUE),
    BROWN(EnumDyeColor.BROWN),
    GREEN(EnumDyeColor.GREEN),
    RED(EnumDyeColor.RED),
    BLACK(EnumDyeColor.BLACK);

    public final EnumDyeColor dyeColor;

    LanternColor(EnumDyeColor dyeColor){
        this.dyeColor = dyeColor;
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
