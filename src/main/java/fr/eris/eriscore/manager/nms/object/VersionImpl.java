package fr.eris.eriscore.manager.nms.object;

import fr.eris.eriscore.api.manager.nms.object.NmsSupport;
import fr.eris.eriscore.api.manager.nms.object.Version;
import fr.eris.eriscore.nms.v1_20_R1.NmsSupport_1_20_R1;
import fr.eris.eriscore.nms.v1_8_R3.NmsSupport_1_8_R3;
import lombok.Getter;

public enum VersionImpl implements Version {
    NOT_FOUND(null),
    v1_8_R3(NmsSupport_1_8_R3.class),
    v1_20_R1(NmsSupport_1_20_R1 .class);

    private final Class<? extends NmsSupport> nmsSupportClass;
    @Getter private final int versionValue;

    VersionImpl(Class<? extends NmsSupport> nmsSupportClass) {
        this.nmsSupportClass = nmsSupportClass;
        this.versionValue = parseVersionAsFullInt(name());
    }

    public int parseVersionAsFullInt(String toParse) {
        try {
            return Integer.parseInt(toParse.replaceAll("[vR_]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public NmsSupport buildNmsSupport() {
        if(nmsSupportClass == null) return null;
        try {
            return nmsSupportClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isNewerThan(Version version) {
        return this.versionValue > ((VersionImpl)version).versionValue;
    }

    public boolean isOlderThan(Version version) {
        return this.versionValue < ((VersionImpl)version).versionValue;
    }

    public boolean isSame(Version version) {
        return this.versionValue == ((VersionImpl)version).versionValue;
    }
}
