package fr.eris.erisutils;

import fr.eris.erisutils.manager.config.ConfigManager;
import fr.eris.erisutils.manager.debugger.DebuggerManager;
import fr.eris.erisutils.manager.debugger.object.Debugger;
import fr.eris.erisutils.manager.nms.NmsAdaptaterManager;
import fr.eris.erisutils.utils.file.FileCache;
import fr.eris.erisutils.utils.manager.ManagerEnabler;
import fr.eris.erisutils.utils.manager.ManagerPriority;
import fr.eris.erisutils.utils.manager.Priority;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.HIGHEST) @Getter private static NmsAdaptaterManager nmsAdaptaterManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static DebuggerManager debuggerManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static ConfigManager configManager;

    public void start(){}
    public void stop(){}

    public final void onEnable() {
        instance = this;
        FileCache.setupFile();
        ManagerEnabler.init(this);
        Debugger.getDebugger("DebugEris").debug("TestDebug");
        Debugger.getDebugger("DebugEris").debug("TestDebug");
        Debugger.getDebugger("DebugEris").debug("TestDebug");
        Debugger.getDebugger("DebugEris").debug("TestDebug");
        start();
    }

    public final void onDisable() {
        ManagerEnabler.stop(this);
        stop();
    }
}
