package fr.eris.eriscore;

import fr.eris.eriscore.commands.ErisCoreCommand;
import fr.eris.eriscore.manager.command.CommandManager;
import fr.eris.eriscore.manager.config.ConfigManager;
import fr.eris.eriscore.manager.database.DataBaseManager;
import fr.eris.eriscore.manager.database.database.object.DataBaseCredential;
import fr.eris.eriscore.manager.database.database.object.DataBaseType;
import fr.eris.eriscore.manager.database.event.OnDataBaseConnect;
import fr.eris.eriscore.manager.debugger.DebuggerManager;
import fr.eris.eriscore.manager.inventory.InventoryManager;
import fr.eris.eriscore.manager.language.LanguageManager;
import fr.eris.eriscore.manager.nms.NmsAdaptaterManager;
import fr.eris.eriscore.utils.file.FileCache;
import fr.eris.eriscore.utils.manager.ManagerEnabler;
import fr.eris.eriscore.utils.manager.ManagerPriority;
import fr.eris.eriscore.utils.manager.Priority;
import fr.eris.eriscore.utils.task.TaskUtils;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.HIGHEST) @Getter private static DebuggerManager debuggerManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static NmsAdaptaterManager nmsAdaptaterManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static LanguageManager languageManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static DataBaseManager dataBaseManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static ConfigManager configManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static CommandManager commandManager;
    @ManagerPriority(init = Priority.LOWEST) @Getter private static InventoryManager inventoryManager;

    private ErisCoreCommand erisCoreCommand;

    public static void postEvent(Event event) {
        ErisCore.getInstance().getServer().getPluginManager().callEvent(event);
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
