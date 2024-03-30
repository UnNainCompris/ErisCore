package fr.eris.eriscore.commands;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.commands.inventory.TestInventory;
import fr.eris.eriscore.manager.command.object.IErisCommand;
import fr.eris.eriscore.manager.command.object.arguments.StringCommandArgument;
import fr.eris.eriscore.api.manager.commands.object.ErisCommand;
import fr.eris.eriscore.api.manager.commands.object.argument.ErisCommandArgument;
import fr.eris.eriscore.api.manager.commands.object.error.ExecutionError;
import fr.eris.eriscore.manager.database.database.mongo.MongoDataBase;
import fr.eris.eriscore.manager.database.database.mongo.MongoDocument;
import fr.eris.eriscore.manager.database.database.object.DataBaseQuery;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.utils.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * The main a global command for eriscore
 */
public class ErisCoreCommand extends IErisCommand {
    public ErisCoreCommand() {
        super("ErisCore", AvailableSender.CONSOLE_AND_PLAYER,
                "eriscore.eriscore", Collections.singletonList("Eris"));

        registerCommand();
    }

    public Collection<ErisCommand> registerSubCommand() {
        return null;
    }

    public Collection<ErisCommandArgument<?>> registerCommandArgument() {
        return Arrays.asList(new StringCommandArgument("action", false, true,
                (player) -> Arrays.asList("inventory", "database")));
    }

    public void execute(CommandSender sender) {
        String todo = retrieveArgumentValue(StringCommandArgument.class, "action");

        if(todo.equalsIgnoreCase("inventory"))
            inventory(sender);
        if(todo.equalsIgnoreCase("database"))
            database(sender);
    }

    @Override
    public void handleError(CommandSender sender, ExecutionError error, String[] args) {

    }

    public void inventory(CommandSender sender) {
        Player player = (Player) sender;
        TestInventory newInv = new TestInventory(player);
        newInv.openInventory();
        newInv.registerAnimations(new IAnimation() {

            public void onStartAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                addAnimationStep((player) -> ItemBuilder.air().setMaterial(Material.GOLDEN_APPLE).build(),
                        9, 200);
            }

            public void onStopAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                addAnimationStep((player) -> ItemBuilder.air().setMaterial(Material.GOLDEN_APPLE).build(),
                        17, 200);
            }
            int counter = 0;
            public void processAnimation(ErisAnimatedInventory erisInventory, long deltaTick) {
                if(counter == 9) counter = 0;
                addAnimationStep((player) -> ItemBuilder.air().setMaterial(Material.STICK).build(),
                        (clickData) -> {
                            ErisCore.getDebugger().info("GetClicked!");
                            clickData.getEvent().setCancelled(true);
                        },
                        counter, 5);
                counter += 1;
            }
        });
    }

    public void database(CommandSender sender) {
        MongoDataBase mongoDataBase = ErisCore.getDataBaseManager().retrieveDataBase(
                "readUser", "admin", MongoDataBase.class);

        mongoDataBase.insertIfAbsent(DataBaseQuery.createQuery("PlayerId", 0));

        MongoDocument mongoDocument = mongoDataBase.findFirst(DataBaseQuery.createQuery("PlayerId", 0));

        mongoDocument.set("PlayerMoney", mongoDocument.getOrDefault("PlayerMoney", 0) + 1);
    }
}
