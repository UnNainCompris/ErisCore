package fr.eris.eriscore.nms.v1_8_R3;

import fr.eris.eriscore.api.manager.nms.object.NmsSupport;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class NmsSupport_1_8_R3 implements NmsSupport {

    public CommandMap retrieveCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch(NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
