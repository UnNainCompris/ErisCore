package fr.eris.eriscore.manager.nms;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.nms.object.Version;
import fr.eris.eriscore.nms.api.NmsSupport;
import fr.eris.eriscore.utils.manager.Manager;
import lombok.Getter;
import org.bukkit.Bukkit;

public class NmsAdaptaterManager extends Manager<ErisCore> {

    @Getter private static Version serverNmsVersion;
    @Getter private static NmsSupport nmsSupport;

    public void findNmsSupport() {
        Debugger.getDebugger("ErisCore").info("Loading NmsSupport");
        serverNmsVersion = Version.findVersion(parent.getServer().getBukkitVersion());
        if(serverNmsVersion == Version.NOT_FOUND) {
            Debugger.getDebugger("ErisCore").severe("No NmsSupport was found. Stopping the plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin(parent);
        } else {
            Debugger.getDebugger("ErisCore").info("NmsSupport version found: " +
                    serverNmsVersion.name() + " {" + serverNmsVersion.getVersionValue() + "}");
        }
        nmsSupport = serverNmsVersion.buildNmsSupport();
    }

    public void start() {
        findNmsSupport();
    }

    public void stop() {

    }
}
