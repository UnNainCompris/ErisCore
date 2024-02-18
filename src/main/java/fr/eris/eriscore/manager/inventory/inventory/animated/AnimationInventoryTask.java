package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.debugger.object.Debugger;
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
        start();
    }

    public void animate(ErisTask executionTask) {
        // creating a new list to avoid concurrent modification error. (Because we work in Async)
        boolean anyProcessed = false;
        attachedInventory.updateAnimation();
        for(IAnimation animation : new ArrayList<>(attachedInventory.getAnimations())) {
            if(animation.process(executionTask.getTickSinceStart())) anyProcessed = true;
        }
        if(anyProcessed)
            attachedInventory.updateInventory();
    }

    public void removeAnimation(IAnimation animationToRemove) {
        animationToRemove.onStopAnimation(attachedInventory, this.getTickSinceStart());
        animationToRemove.isCancelled = true;
    }
}
