package fr.eris.nms.v1_8_R3;

import fr.eris.eriscore.nms.api.NmsSupport;
import org.bukkit.Bukkit;
import org.fusesource.jansi.Ansi;

public class NmsSupport_1_8_R3 extends NmsSupport {

    public void test() {

        Bukkit.getLogger().info( Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString() + Bukkit.getServer().getConsoleSender().getClass().getName() + " !");
        Bukkit.getServer().getConsoleSender().sendMessage("\033[F\r" + this.getClass().getName() + " !2222");
        System.out.println("test " + Ansi.ansi().reset().toString());
    }

}
