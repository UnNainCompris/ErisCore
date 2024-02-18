package fr.eris.eriscore.manager.inventory.inventory.animated.animation;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.AnimationInventoryTask;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimationData;
import fr.eris.eriscore.manager.inventory.item.ClickAction;
import fr.eris.eriscore.manager.inventory.item.ErisInventoryItem;
import fr.eris.eriscore.manager.inventory.item.ItemUpdater;
import lombok.Getter;
import lombok.Setter;

public abstract class IAnimation {
    public boolean isCancelled = false;
    @Setter private ErisAnimatedInventory attachedInventory;

    @Getter private long tickExecutionSpan = 1, lastExecutionTick = -1,
                         executionCount = 0;

    public void setTickExecutionSpan(long newTickExecutionSpan) {
        if(tickExecutionSpan <= 0) throw new IllegalArgumentException("The Tick execution span cannot " +
                "be negative or equals to zero");
        tickExecutionSpan = newTickExecutionSpan;
    }

    public boolean process(long deltaTick) {
        if(isCancelled) return false;
        if(executionCount == 0)
            onStartAnimation(attachedInventory, deltaTick);
        if(deltaTick - lastExecutionTick < tickExecutionSpan) return false;
        executionCount += 1;
        processAnimation(attachedInventory, deltaTick);
        lastExecutionTick = deltaTick;

        return true;
    }

    public void addAnimationStep(ItemUpdater itemUpdater, ClickAction clickAction,
                                 int displaySlot, long maxLiveTick) {
        ErisAnimationData animationData = new ErisAnimationData(ErisInventoryItem.create(itemUpdater, clickAction), displaySlot,
                this, maxLiveTick);
        attachedInventory.registerAnimationStep(animationData);
    }

    public void addAnimationStep(ItemUpdater itemUpdater,
                                 int displaySlot, long maxLiveTick) {
        ErisAnimationData animationData = new ErisAnimationData(ErisInventoryItem.create(itemUpdater), displaySlot,
                this, maxLiveTick);
        attachedInventory.registerAnimationStep(animationData);
    }

    public abstract void onStartAnimation(ErisAnimatedInventory erisInventory,
                                         long deltaTick);

    public abstract void onStopAnimation(ErisAnimatedInventory erisInventory,
                                       long deltaTick);

    public abstract void processAnimation(ErisAnimatedInventory erisInventory,
                                          long deltaTick);
}
