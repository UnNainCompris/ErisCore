package fr.eris.eriscore;

import fr.eris.eriscore.manager.command.CommandManager;
import fr.eris.eriscore.manager.config.ConfigManager;
import fr.eris.eriscore.manager.debugger.DebuggerManager;
import fr.eris.eriscore.manager.debugger.object.DebugType;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.language.LanguageManager;
import fr.eris.eriscore.manager.nms.NmsAdaptaterManager;
import fr.eris.eriscore.nms.api.NmsSupport;
import fr.eris.eriscore.utils.file.FileCache;
import fr.eris.eriscore.utils.manager.ManagerEnabler;
import fr.eris.eriscore.utils.manager.ManagerPriority;
import fr.eris.eriscore.utils.manager.Priority;
import fr.eris.eriscore.utils.task.TaskUtils;
import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;
import io.prometheus.metrics.model.snapshots.Unit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ErisCore extends JavaPlugin {

    @Getter public static ErisCore instance;

    @ManagerPriority(init = Priority.HIGHEST) @Getter private static DebuggerManager debuggerManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static NmsAdaptaterManager nmsAdaptaterManager;
    @ManagerPriority(init = Priority.HIGH) @Getter private static LanguageManager languageManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static ConfigManager configManager;
    @ManagerPriority(init = Priority.NORMAL) @Getter private static CommandManager commandManager;
    public void start() {
        try {
            Debugger.getDebugger("ErisCore").info("Here the start of the server !");
            JvmMetrics.builder().register();
            Counter counter = Counter.builder()
                    .name("uptime_seconds_total")
                    .help("total number of seconds since this application was started")
                    .unit(Unit.SECONDS)
                    .register();
            HTTPServer httpServer = HTTPServer.builder().port(8081).buildAndStart();
            counter.inc(1);
            /*TaskUtils.asyncRepeat((erisTask) -> {
                counter.inc(1);
                Debugger.getDebugger("ErisCore").info("inc " + counter.getPrometheusName());
            }, 0L, 1L);*/
            Debugger.getDebugger("ErisCore").info("Here the end of the server !");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void stop() {

    }

    public final void onEnable() {
        instance = this;
        FileCache.setupFile();
        ManagerEnabler.init(this);
        start();
    }

    public final void onDisable() {
        ManagerEnabler.stop(this);
        stop();
    }
}
