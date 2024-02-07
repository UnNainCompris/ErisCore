package fr.eris.erisutils.manager.debugger.object;

import fr.eris.erisutils.ErisCore;
import fr.eris.erisutils.manager.debugger.config.YamlDebuggerConfig;
import fr.eris.erisutils.utils.ColorUtils;

public class Debugger {

    private final String debuggerName;

    private boolean isEnable;
    private String prefix, format;

    public Debugger(String debuggerName) {
        this.debuggerName = debuggerName;
        setup();
    }

    private void setup() {
        if(!YamlDebuggerConfig.getConfig().isDebuggerExist(debuggerName))
            YamlDebuggerConfig.getConfig().loadDefaultConfig(debuggerName);

        isEnable = YamlDebuggerConfig.getConfig().getBoolean(debuggerName + ".enabled");
        prefix = YamlDebuggerConfig.getConfig().getString(debuggerName + ".prefix");
        format = YamlDebuggerConfig.getConfig().getString(debuggerName + ".format");

    }

    public void debug(String toDebug) {
        if(!isEnable) return;

        ErisCore.getInstance().getLogger().info(ColorUtils.translateColor(
                format.replace("%prefix%", prefix)
                        .replace("%message%", toDebug)));
    }

    public static Debugger getDebugger(String debuggerName) {
        debuggerName = debuggerName.toLowerCase();
        return ErisCore.getDebuggerManager().getDebugger(debuggerName);
    }

}
