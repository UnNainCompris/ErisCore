package fr.eris.eriscore.manager.nms.object;

import fr.eris.eriscore.nms.api.NmsSupport;
import fr.eris.eriscore.nms.v1_20_R1.NmsSupport_1_20_R1;
import fr.eris.nms.v1_8_R3.NmsSupport_1_8_R3;
import lombok.Getter;

public enum Version {
    NOT_FOUND(null),
    v1_8_R3(NmsSupport_1_8_R3.class),
    v1_20_R1(NmsSupport_1_20_R1 .class);



    public static Version findVersion(String serverBukkitVersion) {
        return switch (serverBukkitVersion) {
            case "1.8.8-R0.1-SNAPSHOT" -> v1_8_R3;
            case "1.20.1-R0.1-SNAPSHOT" -> v1_20_R1;
            default -> NOT_FOUND;
        };
    }

    private final Class<? extends NmsSupport> nmsSupportClass;
    @Getter private final int versionValue;
    Version(Class<? extends NmsSupport> nmsSupportClass) {
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
        return this.versionValue > version.versionValue;
    }

    public boolean isOlderThan(Version version) {
        return this.versionValue < version.versionValue;
    }

    public boolean isSame(Version version) {
        return this.versionValue == version.versionValue;
    }
}
