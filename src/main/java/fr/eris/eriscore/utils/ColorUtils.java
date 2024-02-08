package fr.eris.eriscore.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String translateColor(String toTranslate) {
        return ChatColor.translateAlternateColorCodes(
                '&', toTranslate);
    }
}