package fr.eris.eriscore.utils.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static List<Field> retrieveAnnotatedField(Class<?> targetClass, Class<? extends Annotation> annotationClass) {
        List<Field> foundedField = new ArrayList<>();
        for(Field field : targetClass.getDeclaredFields()) {
            if(field.getDeclaredAnnotation(annotationClass) != null)
                foundedField.add(field);
        }
        return foundedField;
    }

    public static List<Field> retrieveAnnotatedFieldOfType(Class<?> targetClass, Class<?> requiredType, Class<? extends Annotation> annotationClass) {
        List<Field> foundedField = new ArrayList<>();
        for(Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getDeclaredAnnotation(annotationClass) != null && requiredType.isAssignableFrom(field.getType()))
                foundedField.add(field);
        }
        return foundedField;
    }

}
