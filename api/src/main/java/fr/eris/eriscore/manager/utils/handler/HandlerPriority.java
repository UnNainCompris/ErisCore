package fr.eris.eriscore.manager.utils.handler;

import fr.eris.eriscore.manager.utils.Priority;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerPriority {
    Priority init() default Priority.NORMAL;
    Priority stop() default Priority.NORMAL;
}
