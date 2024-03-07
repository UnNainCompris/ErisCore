package fr.eris.eriscore.manager.nms;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.nms.object.NmsSupport;
import fr.eris.eriscore.manager.nms.object.Version;
import fr.eris.eriscore.manager.nms.object.VersionImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

public class NmsSupportManagerImpl implements NmsSupportManager<ErisCore> {

    @Getter private static Version serverNmsVersion;
    @Getter private static NmsSupport nmsSupport;
    @Getter @Setter ErisCore parent;

    @Override
    public void findNmsSupport() {
        Debugger.getDebugger().info("Loading NmsSupport");
        serverNmsVersion = findVersion(parent.getServer().getBukkitVersion());
        if(serverNmsVersion == VersionImpl.NOT_FOUND) {
            Debugger.getDebugger().severe("No NmsSupport was found. Stopping the plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin(parent);
        } else {
            Debugger.getDebugger().info("NmsSupport version found: " +
                    serverNmsVersion.name() + " {" + serverNmsVersion.getVersionValue() + "}");
        }
        nmsSupport = serverNmsVersion.buildNmsSupport();
    }

    @Override
    public Version findVersion(String serverBukkitVersion) {
        if (serverBukkitVersion.equals("1.8.8-R0.1-SNAPSHOT"))
            return VersionImpl.v1_8_R3;
        if (serverBukkitVersion.equals("1.20.1-R0.1-SNAPSHOT"))
            return VersionImpl.v1_20_R1;
        return VersionImpl.NOT_FOUND;
    }

    @Override
    public void start() {
        findNmsSupport();
    }

    @Override
    public void stop() {

    }
}
