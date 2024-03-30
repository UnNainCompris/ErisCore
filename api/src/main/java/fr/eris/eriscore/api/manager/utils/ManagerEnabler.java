package fr.eris.eriscore.api.manager.utils;

import fr.eris.eriscore.api.manager.utils.handler.HandlerEnabler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManagerEnabler {
    public static <T> void init(T instance) {
        List<Field> managerFields = new ArrayList<>();

        for (Field field : instance.getClass().getDeclaredFields()) {
            if (!Manager.class.isAssignableFrom(field.getType())) continue;
            managerFields.add(field);
        }

        managerFields.sort(Comparator.comparingInt(managerField -> {
            Priority priority = Priority.NORMAL;
            final ManagerPriority managerPriority;
            if((managerPriority = managerField.getAnnotation(ManagerPriority.class)) != null) priority = managerPriority.init();
            return priority.getPriorityWeight();
        }));
        Collections.reverse(managerFields);
        initField(managerFields, instance);
    }

    public static <T> void initField(List<Field> fieldToInit, T instance) {
        for(Field managerField : fieldToInit) {
            try {
                if(managerField == null || !Manager.class.isAssignableFrom(managerField.getType())) continue;
                managerField.setAccessible(true);
                if(managerField.get(instance) != null) return;
                Constructor<?> constructor = findConstructor(managerField.getType());
                Manager<T> manager = ((Manager<T>) constructor.newInstance());
                managerField.set(instance, manager);
                manager.setParent(instance);
                HandlerEnabler.init(instance);
                manager.start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Constructor<?> findConstructor(Class<?> managerClazz) {
        if(!Manager.class.isAssignableFrom(managerClazz)) return null;
        ApiClassRedirect classRedirect = managerClazz.getAnnotation(ApiClassRedirect.class);
        try {
            if (classRedirect == null)
                return managerClazz.getDeclaredConstructor();

            Class<?> apiRedirectClass = Class.forName(classRedirect.value());

            if (managerClazz.isAssignableFrom(apiRedirectClass))
                return apiRedirectClass.getDeclaredConstructor();
            else
                throw new RuntimeException("The redirect api class is not assignable from " +
                        "the manager class{" + managerClazz + "}");
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("The api redirect class{" + classRedirect.value() + "} won't be found !");
        } catch (Exception exception) {
            throw new RuntimeException("No default constructor found in " + managerClazz +
                    " or the api redirect class", exception);
        }
    }

    public static <T> void stop(T instance) {
        List<Field> managerFields = new ArrayList<>();

        for (Field field : instance.getClass().getDeclaredFields()) {
            if (!Manager.class.isAssignableFrom(field.getType())) continue;
            managerFields.add(field);
        }

        managerFields.sort(Comparator.comparingInt(managerField -> {
            Priority priority = Priority.NORMAL;
            final ManagerPriority managerPriority;
            if((managerPriority = managerField.getAnnotation(ManagerPriority.class)) != null) priority = managerPriority.stop();
            return priority.getPriorityWeight();
        }));

        Collections.reverse(managerFields);
        for(Field managerField : managerFields) {
            managerField.setAccessible(true);
            try {
                if (managerField.get(instance) != null) {
                    Manager<?> manager = ((Manager<?>) managerField.get(instance));
                    HandlerEnabler.stop(instance);
                    manager.stop();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
