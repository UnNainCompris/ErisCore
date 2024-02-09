package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.utils.task.ErisTask;

import java.util.Arrays;
import java.util.List;

public class AnimationInventoryTask extends ErisTask {

    private final ErisAnimatedInventory attachedInventory;

    public AnimationInventoryTask(ErisAnimatedInventory attachedInventory) {
        super(null, true, 0, 1);
        if(attachedInventory == null)
            throw new IllegalArgumentException("Inventory cannot be null");
        this.attachedInventory = attachedInventory;
    }

    public void animate(ErisTask executionTask) {

    }
}
