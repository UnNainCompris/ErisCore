package fr.eris.eriscore.manager.database.event;

import fr.eris.eriscore.manager.database.database.DataBase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class DataBaseEvent extends Event implements Cancellable {
    @Getter private final DataBase<?> dataBase;
    @Getter @Setter private boolean cancelled;

    protected DataBaseEvent(DataBase<?> dataBase) {
        this.dataBase = dataBase;
    }
}
