package fr.eris.eriscore.api.manager.nms.object;

public interface Version {
    int parseVersionAsFullInt(String toParse);
    NmsSupport buildNmsSupport();

    boolean isNewerThan(Version version);
    boolean isOlderThan(Version version);
    boolean isSame(Version version);

    String name();
    int getVersionValue();
}

