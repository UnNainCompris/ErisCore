package fr.eris.erisutils.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String translateColor(String toTranslate) {
        return ChatColor.translateAlternateColorCodes(
                '&', toTranslate);
    }
}
