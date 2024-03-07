package fr.eris.eriscore.manager.utils;

import lombok.Getter;

@Getter
public enum Priority {
    SNAIL(0),
    LOWEST(5),
    LOW(10),
    NORMAL(15),
    HIGH(20),
    HIGHEST(25),
    PRIORITIZED(30);

    private final int priorityWeight;

    Priority(int priorityWeight) {
        this.priorityWeight = priorityWeight;
    }
}
