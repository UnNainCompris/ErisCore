package fr.eris.eriscore.manager.debugger;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.api.manager.debugger.DebuggerManager;
import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.manager.debugger.object.DebuggerImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class DebuggerManagerImpl implements DebuggerManager<ErisCore> {

    private final HashMap<String, Debugger> loadedDebugger = new HashMap<>();
    @Getter @Setter private ErisCore parent;

    public void start() {

    }

    public void stop() {

    }

    public Debugger getDebugger(String debuggerName) {
        String defaultDebuggerName = debuggerName;
        debuggerName = debuggerName.toLowerCase();
        if(!loadedDebugger.containsKey(debuggerName)) {
            Debugger newDebugger = new DebuggerImpl(defaultDebuggerName);
            loadedDebugger.put(debuggerName, newDebugger);
            if(newDebugger.isEnabled()) newDebugger.info(debuggerName + " debugger is now loaded ! [Look at debug.yml for configuration]");
        }
        return loadedDebugger.get(debuggerName);
    }

    public HashMap<String, Debugger> getLoadedDebugger() {
        return loadedDebugger;
    }
}
