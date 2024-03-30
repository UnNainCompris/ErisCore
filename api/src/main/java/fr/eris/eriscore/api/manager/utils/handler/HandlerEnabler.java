package fr.eris.eriscore.api.manager.utils.handler;

import fr.eris.eriscore.api.manager.utils.Priority;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HandlerEnabler {
    public static <T> void init(T instance) {
        List<Field> handlerFields = new ArrayList<>();

        for (Field field : instance.getClass().getDeclaredFields()) {
            if (!Handler.class.isAssignableFrom(field.getType())) continue;
            handlerFields.add(field);
        }

        handlerFields.sort(Comparator.comparingInt(handlerField -> {
            Priority priority = Priority.NORMAL;
            final HandlerPriority handlerPriority;
            if((handlerPriority = handlerField.getAnnotation(HandlerPriority.class)) != null) priority = handlerPriority.init();
            return priority.getPriorityWeight();
        }));
        Collections.reverse(handlerFields);
        initField(handlerFields, instance);
    }

    public static <T> void initField(List<Field> fieldToInit, T instance) {
        for(Field handlerField : fieldToInit) {
            try {
                if(handlerField == null || !Handler.class.isAssignableFrom(handlerField.getType())) continue;
                handlerField.setAccessible(true);
                if(handlerField.get(instance) != null) return;
                Constructor<?> constructor = handlerField.getType().getDeclaredConstructor();
                Handler<T> handler = ((Handler<T>) constructor.newInstance());
                handlerField.set(instance, handler);
                handler.setParent(instance);
                handler.start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> void stop(T instance) {
        List<Field> handlerFields = new ArrayList<>();

        for (Field field : instance.getClass().getDeclaredFields()) {
            if (!Handler.class.isAssignableFrom(field.getType())) continue;
            handlerFields.add(field);
        }

        handlerFields.sort(Comparator.comparingInt(handlerField -> {
            Priority priority = Priority.NORMAL;
            final HandlerPriority handlerPriority;
            if((handlerPriority = handlerField.getAnnotation(HandlerPriority.class)) != null) priority = handlerPriority.stop();
            return priority.getPriorityWeight();
        }));

        Collections.reverse(handlerFields);
        for(Field handlerField : handlerFields) {
            handlerField.setAccessible(true);
            try {
                if (handlerField.get(instance) != null) {
                    Handler<?> handler = ((Handler<?>) handlerField.get(instance));
                    handler.stop();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
