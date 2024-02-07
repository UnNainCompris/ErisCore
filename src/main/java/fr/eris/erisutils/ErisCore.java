package fr.eris.erisutils;

import fr.eris.erisutils.manager.config.ConfigManager;
import fr.eris.erisutils.manager.debugger.DebuggerManager;
import fr.eris.erisutils.utils.file.FileCache;
import fr.eris.erisutils.utils.manager.ManagerEnabler;
import fr.eris.erisutils.utils.manager.ManagerPriority;
import fr.eris.erisutils.utils.manager.Priority;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.HIGHEST) @Getter private static DebuggerManager debuggerManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static ConfigManager configManager;

    public void onEnable() {
        instance = this;
        FileCache.setupFile();
        ManagerEnabler.init(this);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }
}
