package fr.eris.eriscore.nms.v1_20_R1;

import fr.eris.eriscore.manager.nms.object.NmsSupport;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class NmsSupport_1_20_R1 implements NmsSupport {
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
