package fr.eris.eriscore.api.manager.debugger;

import fr.eris.eriscore.api.manager.debugger.object.Debugger;
import fr.eris.eriscore.api.manager.utils.ApiClassRedirect;
import fr.eris.eriscore.api.manager.utils.Manager;

import java.util.HashMap;

@ApiClassRedirect("fr.eris.eriscore.manager.debugger.DebuggerManagerImpl")
public interface DebuggerManager<T> extends Manager<T> {
    Debugger getDebugger(String debuggerName);
    HashMap<String, Debugger> getLoadedDebugger();
}
