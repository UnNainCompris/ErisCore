package fr.eris.erisutils.manager.debugger;

import fr.eris.erisutils.ErisCore;
import fr.eris.erisutils.manager.debugger.object.Debugger;
import fr.eris.erisutils.utils.manager.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DebuggerManager extends Manager<ErisCore> {

    private final HashMap<String, Debugger> loadedDebugger = new HashMap<>();

    public void start() {

    }

    public void stop() {

    }

    public Debugger getDebugger(String debuggerName) {
        debuggerName = debuggerName.toLowerCase();

        if(!loadedDebugger.containsKey(debuggerName))
            loadedDebugger.put(debuggerName, new Debugger(debuggerName));
        return loadedDebugger.get(debuggerName);
    }
}
