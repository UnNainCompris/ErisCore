package fr.eris.eriscore.manager.inventory.inventory.animated.animation;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.AnimationInventoryTask;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
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

    public void process(long deltaTick) {
        if(isCancelled) return;
        if(executionCount == 0)
            onStartAnimation(attachedInventory, deltaTick);
        if(deltaTick - lastExecutionTick <= tickExecutionSpan) return;
        executionCount += 1;
        lastExecutionTick = deltaTick;
        processAnimation(attachedInventory, deltaTick);
    }

    public abstract void onStartAnimation(ErisAnimatedInventory erisInventory,
                                         long deltaTick);

    public abstract void onStopAnimation(ErisAnimatedInventory erisInventory,
                                       long deltaTick);

    public abstract void processAnimation(ErisAnimatedInventory erisInventory,
                                          long deltaTick);
}
