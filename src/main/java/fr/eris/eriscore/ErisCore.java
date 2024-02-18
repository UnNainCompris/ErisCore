package fr.eris.eriscore;

import fr.eris.eriscore.commands.ErisCoreCommand;
import fr.eris.eriscore.manager.command.CommandManager;
import fr.eris.eriscore.manager.config.ConfigManager;
import fr.eris.eriscore.manager.debugger.DebuggerManager;
import fr.eris.eriscore.manager.language.LanguageManager;
import fr.eris.eriscore.manager.nms.NmsAdaptaterManager;
import fr.eris.eriscore.utils.file.FileCache;
import fr.eris.eriscore.utils.manager.ManagerEnabler;
import fr.eris.eriscore.utils.manager.ManagerPriority;
import fr.eris.eriscore.utils.manager.Priority;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.HIGHEST) @Getter private static DebuggerManager debuggerManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static NmsAdaptaterManager nmsAdaptaterManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static LanguageManager languageManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static ConfigManager configManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static CommandManager commandManager;

    private ErisCoreCommand erisCoreCommand;


    public final void onEnable() {
        instance = this;
        FileCache.setupFile();
        ManagerEnabler.init(this);
        erisCoreCommand = new ErisCoreCommand();
    }

    public final void onDisable() {
        ManagerEnabler.stop(this);
    }
}
