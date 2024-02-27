package fr.eris.eriscore.manager.database.event;

import fr.eris.eriscore.manager.database.database.DataBase;
import lombok.Getter;
import org.bukkit.event.HandlerList;

public class OnDataBaseConnect extends DataBaseEvent {

    @Getter private final HandlerList handlers = new HandlerList();

    public OnDataBaseConnect(DataBase<?> dataBase) {
        super(dataBase);
    }
}
