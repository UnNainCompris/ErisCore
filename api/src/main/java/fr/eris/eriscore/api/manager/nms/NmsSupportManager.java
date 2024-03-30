package fr.eris.eriscore.api.manager.nms;

import fr.eris.eriscore.api.manager.nms.object.NmsSupport;
import fr.eris.eriscore.api.manager.nms.object.Version;
import fr.eris.eriscore.api.manager.utils.ApiClassRedirect;
import fr.eris.eriscore.api.manager.utils.Manager;

@ApiClassRedirect("fr.eris.eriscore.manager.nms.NmsSupportManagerImpl")
public interface NmsSupportManager<T> extends Manager<T> {
    Version getServerNmsVersion();
    NmsSupport getNmsSupport();

    void findNmsSupport();
    Version findVersion(String serverBukkitVersion);
}
