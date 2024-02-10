package fr.eris.eriscore.manager.command.object.annotation;

public @interface ErisExecutionParameter {
    String executionIdentifier();
    String[] requiredArgsAsName();
}
