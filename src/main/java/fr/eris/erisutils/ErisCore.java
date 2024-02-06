package fr.eris.erisutils;

import fr.eris.erisutils.nms.api.NmsSupport;
import fr.eris.erisutils.nms.v1_20_R1.NmsSupport_1_20_R1;
import fr.eris.erisutils.utils.file.FileCache;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    public void onEnable() {
        instance = this;
        FileCache.setupFile();
    }

    public void onDisable() {
        // Plugin shutdown logic
    }
}
