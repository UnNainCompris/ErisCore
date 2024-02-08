package fr.eris.eriscore.manager.debugger;

import fr.eris.eriscore.ErisCore;
import fr.eris.eriscore.manager.debugger.object.Debugger;
import fr.eris.eriscore.utils.manager.Manager;

import java.util.HashMap;

public class DebuggerManager extends Manager<ErisCore> {

    private final HashMap<String, Debugger> loadedDebugger = new HashMap<>();

    public void start() {

    }

    public void stop() {

    }

    public Debugger getDebugger(String debuggerName) {
        String defaultDebuggerName = debuggerName;
        debuggerName = debuggerName.toLowerCase();
        if(!loadedDebugger.containsKey(debuggerName)) {
            Debugger newDebugger = new Debugger(defaultDebuggerName);
            loadedDebugger.put(debuggerName, newDebugger);
            if(newDebugger.isEnable()) newDebugger.info("Is now loaded ! [Look at debug.yml for configuration]");
        }
        return loadedDebugger.get(debuggerName);
    }
}
