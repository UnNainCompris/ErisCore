package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventoryHolder;
import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ErisAnimatedInventory extends ErisInventory {

    @Getter private final List<IAnimation> animations;
    private AnimationInventoryTask animationTask;

    public ErisAnimatedInventory(Player target, String inventoryName, int inventoryRowAmount) {
        this(target, inventoryName, inventoryRowAmount, null);
    }

    public ErisAnimatedInventory(Player target, String inventoryName, int inventoryRowAmount,
                                 ErisInventoryHolder inventoryHolder) {
        super(target, inventoryName, inventoryRowAmount, inventoryHolder);
        this.animations = new ArrayList<>();
        this.animationTask = new AnimationInventoryTask(this);
    }

    public void registerAnimations(IAnimation... newAnimations) {
        if(newAnimations == null) return;
        animations.addAll(Arrays.asList(newAnimations));
    }

    public void unRegisterAnimation(IAnimation animationToRemove) {
        animations.remove(animationToRemove);
        animationTask.removeAnimation(animationToRemove);
    }
}
