package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.utils.task.ErisTask;

import java.util.ArrayList;

public class AnimationInventoryTask extends ErisTask {

    private final ErisAnimatedInventory attachedInventory;

    public AnimationInventoryTask(ErisAnimatedInventory attachedInventory) {
        super(null, true, 0, 1);
        if(attachedInventory == null)
            throw new IllegalArgumentException("Inventory cannot be null");
        this.attachedInventory = attachedInventory;
        this.setAction(this::animate);
    }

    public void animate(ErisTask executionTask) {
        // creating a new list to avoid concurrent modification error. (Because we work in Async)
        for(IAnimation animation : new ArrayList<>(attachedInventory.getAnimations())) {
            animation.process(executionTask.getTickSinceStart());
        }
        attachedInventory.updateInventory();
    }

    public void removeAnimation(IAnimation animationToRemove) {
        animationToRemove.onStopAnimation(attachedInventory, this.getTickSinceStart());
    }
}
