package fr.eris.eriscore.commands;

import fr.eris.eriscore.commands.inventory.TestInventory;
import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.command.object.error.ExecutionError;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.utils.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The main a global command for eriscore
 */
public class ErisCoreCommand extends IErisCommand {
    public ErisCoreCommand() {
        super("ErisCore", AvailableSender.CONSOLE_AND_PLAYER,
                "eriscore.eriscore", Collections.singletonList("Eris"));
    }

    public void registerSubCommand() {

    }

    public void registerCommandArgument() {

    }

    public void execute(CommandSender sender) {
        Player player = (Player) sender;
        TestInventory newInv = new TestInventory(player);
        newInv.openInventory();
        newInv.registerAnimations(new IAnimation() {
            public void onStartAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                Debugger.getDebugger("ErisCore").test("Start animation");
                erisInventory.setItem(1, (player) -> ItemBuilder.create().setMaterial(Material.GOLDEN_APPLE).build());
            }

            public void onStopAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                Debugger.getDebugger("ErisCore").test("Stop animation");
                erisInventory.setItem(10, (player) -> ItemBuilder.create().setMaterial(Material.GOLDEN_APPLE).build());
            }

            public void processAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                Debugger.getDebugger("ErisCore").test("Process animation");
                erisInventory.setItem((int)(deltaTick % 40), (player) -> ItemBuilder.create().setMaterial(Material.STICK).build());

            }
        });
    }

    public void handleError(CommandSender sender, ExecutionError error, String[] args) {

    }
}
