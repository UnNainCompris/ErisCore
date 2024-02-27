package fr.eris.eriscore.manager.database.event;

import fr.eris.eriscore.manager.database.database.DataBase;
import lombok.Getter;
import org.bukkit.event.HandlerList;

public class OnDataBaseDisconnect extends DataBaseEvent {

    @Getter private final HandlerList handlers = new HandlerList();

    public OnDataBaseDisconnect(DataBase<?> dataBase) {
        super(dataBase);
    }
}
