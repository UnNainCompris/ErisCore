package fr.eris.erisutils.manager.nms;

import fr.eris.erisutils.ErisCore;
import fr.eris.erisutils.manager.nms.object.Version;
import fr.eris.erisutils.nms.api.NmsSupport;
import fr.eris.erisutils.utils.manager.Manager;
import lombok.Getter;

public class NmsAdaptaterManager extends Manager<ErisCore> {

    @Getter private static Version serverNmsVersion;
    @Getter private static NmsSupport nmsSupport;

    public void findNmsSupport() {
        serverNmsVersion = Version.findVersion(parent.getServer().getBukkitVersion());
        nmsSupport = serverNmsVersion.buildNmsSupport();
    }

    public void start() {
        findNmsSupport();
    }

    public void stop() {

    }
}
