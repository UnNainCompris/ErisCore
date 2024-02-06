package fr.eris.erisutils.utils.file;

import fr.eris.erisutils.ErisCore;
import lombok.Getter;

import java.io.File;

public class FileCache {

    public static File PLUGIN_DIR;
    public static File SERVER_DIR;
    public static File SPIGOT_PLUGIN_DIR;

    public static void setupFile() {
        PLUGIN_DIR = ErisCore.getInstance().getDataFolder();
        SPIGOT_PLUGIN_DIR = PLUGIN_DIR.getParentFile();
        SERVER_DIR = SPIGOT_PLUGIN_DIR.getParentFile();
    }
}
