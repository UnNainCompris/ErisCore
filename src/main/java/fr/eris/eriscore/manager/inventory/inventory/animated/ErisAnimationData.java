package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.manager.inventory.item.ErisInventoryItem;
import lombok.Getter;

@Getter
public class ErisAnimationData {
    private final ErisInventoryItem item;
    private final int displaySlot;
    private final IAnimation animationParent;
    private final long maxLiveTick;
    private long currentLiveTick;

    public ErisAnimationData(ErisInventoryItem item,
                             int displaySlot, IAnimation animationParent, long maxLiveTick) {
        this.item = item;
        this.displaySlot = displaySlot;
        this.animationParent = animationParent;
        this.maxLiveTick = maxLiveTick;
    }

    public void update(long deltaTime) {
        currentLiveTick += (deltaTime - animationParent.getLastExecutionTick());
    }

    public boolean isAlive() {
        return currentLiveTick < maxLiveTick;
    }
}
