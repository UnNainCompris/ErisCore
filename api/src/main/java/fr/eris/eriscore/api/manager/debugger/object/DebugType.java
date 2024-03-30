package fr.eris.eriscore.api.manager.debugger.object;

import lombok.Getter;

public enum DebugType {
    INFO("&7[&eINFO&7]"), // For info
    WARNING("&7[&cWARNING&7]"), // When something is wrong but we can work without it
    ERROR("&7[&4&lERROR&7]"), // When the plugin can't work without it
    SEVERE("&7[&4SEVERE&7]"), // When the plugin lose some functionality without it
    TEST("&7[&8TEST&7]"), // for real development debugging purpose
    DEVELOPMENT("&7[&8DEVELOPMENT&7]"); // For nerd

    @Getter
    private final String display;

    DebugType(String display) {
        this.display = display;
    }
}
