package fr.eris.eriscore.utils.bukkit.color;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String translateColor(String toTranslate) {
        return ChatColor.translateAlternateColorCodes(
                '&', toTranslate);
    }

    public static String stripColor(String toTranslate) {
        return ChatColor.stripColor(toTranslate);
    }
}
