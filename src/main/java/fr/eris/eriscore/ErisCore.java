package fr.eris.eriscore;

import fr.eris.eriscore.api.manager.commands.CommandManager;
import fr.eris.eriscore.api.manager.debugger.DebuggerManager;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.api.manager.file.FileManager;
import fr.eris.eriscore.api.manager.language.LanguageManager;
import fr.eris.eriscore.api.manager.nms.NmsSupportManager;
import fr.eris.eriscore.commands.ErisCoreCommand;
import fr.eris.eriscore.manager.command.CommandManagerImpl;
import fr.eris.eriscore.manager.file.FileManagerImpl;
import fr.eris.eriscore.manager.database.DataBaseManager;
import fr.eris.eriscore.manager.database.database.object.DataBaseCredential;
import fr.eris.eriscore.manager.database.database.object.DataBaseType;
import fr.eris.eriscore.manager.debugger.DebuggerManagerImpl;
import fr.eris.eriscore.manager.inventory.InventoryManager;
import fr.eris.eriscore.manager.nms.NmsSupportManagerImpl;
import fr.eris.eriscore.utils.file.FileCache;
import fr.eris.eriscore.api.manager.utils.ManagerEnabler;
import fr.eris.eriscore.api.manager.utils.ManagerPriority;
import fr.eris.eriscore.api.manager.utils.Priority;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.PRIORITIZED) @Getter private static NmsSupportManager<ErisCore> nmsSupportManager;
    @ManagerPriority(init = Priority.HIGHEST) @Getter private static DebuggerManager<ErisCore> debuggerManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static LanguageManager<ErisCore> languageManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static DataBaseManager dataBaseManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static FileManager<ErisCore> configManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static CommandManager<ErisCore> commandManager;
    @ManagerPriority(init = Priority.LOWEST) @Getter private static InventoryManager inventoryManager;

    private ErisCoreCommand erisCoreCommand;

    public static void postEvent(Event event) {
        ErisCore.getInstance().getServer().getPluginManager().callEvent(event);
    }

    public static Debugger getDebugger() {
        return debuggerManager.getDebugger(instance.getName());
    }

    public static Debugger getDebugger(String debuggerName) {
        return debuggerManager.getDebugger(debuggerName);
    }


    public final void onEnable() {
        instance = this;
        FileCache.setupFile();
        ManagerEnabler.init(this);
        erisCoreCommand = new ErisCoreCommand();

        dataBaseManager.loadIfAbsentDatabase(DataBaseType.MONGO,
                DataBaseCredential.build("admin", "127.0.0.1",
                        "27017", "password", "readUser"));
    }

    public final void onDisable() {
        ManagerEnabler.stop(this);
    }
}
