package fr.eris.eriscore.manager.inventory.inventory.animated.animation;

import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.animated.AnimationInventoryTask;
import fr.eris.eriscore.manager.inventory.inventory.animated.ErisAnimatedInventory;
import lombok.Setter;

public abstract class IAnimation {
    public boolean isCancelled = false;
    @Setter private ErisAnimatedInventory attachedInventory;

    private long tickSpan, lastExecutionTick = -1;

    public void process(int deltaTick) {
        if(isCancelled) return;
        if(lastExecutionTick == -1)
            onStartAnimation(attachedInventory, deltaTick);
        if(deltaTick - lastExecutionTick <= tickSpan) return;
        lastExecutionTick = deltaTick;
        processAnimation(attachedInventory, deltaTick);
    }

    public abstract void onStartAnimation(ErisAnimatedInventory erisInventory,
                                         int deltaTick);

    public abstract void onStopAnimation(ErisAnimatedInventory erisInventory,
                                       int deltaTick);

    public abstract void processAnimation(ErisAnimatedInventory erisInventory,
                                          int deltaTick);
}
