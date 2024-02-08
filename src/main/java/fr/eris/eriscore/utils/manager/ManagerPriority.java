package fr.eris.eriscore.utils.manager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerPriority {
    Priority init() default Priority.NORMAL;
    Priority stop() default Priority.NORMAL;
}
