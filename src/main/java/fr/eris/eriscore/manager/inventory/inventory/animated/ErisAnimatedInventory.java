package fr.eris.eriscore.manager.inventory.inventory.animated;

import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventory;
import fr.eris.eriscore.manager.inventory.inventory.ErisInventoryHolder;
import fr.eris.eriscore.manager.inventory.inventory.animated.animation.IAnimation;
import fr.eris.eriscore.manager.inventory.item.ClickAction;
import fr.eris.eriscore.manager.inventory.item.ErisInventoryItem;
import fr.eris.eriscore.utils.bukkit.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class ErisAnimatedInventory extends ErisInventory {

    @Getter private final List<IAnimation> animations;
    private final AnimationInventoryTask animationTask;

    private final HashMap<Integer, ErisAnimationData> animationDataList;

    public ErisAnimatedInventory(Player target, String inventoryName, int inventoryRowAmount) {
        this(target, inventoryName, inventoryRowAmount, null);
    }

    public ErisAnimatedInventory(Player target, String inventoryName, int inventoryRowAmount,
                                 ErisInventoryHolder inventoryHolder) {
        super(target, inventoryName, inventoryRowAmount, inventoryHolder);
        this.animations = new ArrayList<>();
        this.animationDataList = new HashMap<>();
        this.animationTask = new AnimationInventoryTask(this);
    }

    public void updateInventory() {
        super.updateInventory();
        for(ErisAnimationData animationData : new ArrayList<>(animationDataList.values())) {
            if(!animationData.isAlive()) {
                inventory.setItem(animationData.getDisplaySlot(), ItemBuilder.air().build());
                animationDataList.remove(animationData.getDisplaySlot());
                continue;
            }
            inventory.setItem(animationData.getDisplaySlot(),
                    animationData.getItem().getItemUpdater().getItem(viewers));
        }
    }

    public void updateAnimation() {
        for (ErisAnimationData animationData : new ArrayList<>(animationDataList.values())) {
            animationData.update(animationTask.getTickSinceStart());
        }
    }

    public void destroy() {
        super.destroy();
        animationTask.cancel();
        animationDataList.clear();
        animations.clear();
    }

    public void registerAnimations(IAnimation... newAnimations) {
        if(newAnimations == null) return;
        for(IAnimation animation : newAnimations)
            animation.setAttachedInventory(this);
        animations.addAll(Arrays.asList(newAnimations));
    }

    public void unRegisterAnimation(IAnimation animationToRemove) {
        animations.remove(animationToRemove);
        animationTask.removeAnimation(animationToRemove);
    }

    public void registerAnimationStep(ErisAnimationData animationData) {
        animationDataList.put(animationData.getDisplaySlot(), animationData);
    }

    public void onClicked(InventoryClickEvent event) {
        int clickedSlot = event.getSlot();
        if(!animationDataList.containsKey(clickedSlot)) return;
        ErisInventoryItem item = animationDataList.get(clickedSlot).getItem();
        if(item.getClickAction() == null) return;
        item.getClickAction().onClick(new ClickAction.ClickActionData(event, this, inventoryHolder, item));
    }
}
