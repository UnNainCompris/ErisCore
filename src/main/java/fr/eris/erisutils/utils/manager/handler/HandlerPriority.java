package fr.eris.erisutils.utils.manager.handler;

import fr.eris.erisutils.utils.manager.Priority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerPriority {
    Priority init() default Priority.NORMAL;
    Priority stop() default Priority.NORMAL;
}
