package org.coador.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class GetterFactory {

    private static Field getField(String property, Class<?> owner) {
        for (Field field : owner.getDeclaredFields()) {
            if (property.equals(field.getName()))
                return field;
        }
        return null;
    }

    private static Method getMethod(String property, Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (property.equals(method.getName().toUpperCase())
                    && method.getParameterTypes().length == 0)
                return method;
        }
        return null;
    }

    private static AbstractGetter newFieldGetter(Field field,
            AbstractGetter parent) {

        AbstractGetter getter = new FieldGetter(field);
        if (parent != null)
            parent.setChild(getter);

        return getter;
    }

    public static Getter newGetter(Class<?> owner, String path) {
        String[] ss = path.split("\\.");
        AbstractGetter getter = null;
        Getter mainGetter = null;
        for (String property : ss) {
            property = property.toUpperCase();
            Method method = getMethod("GET".concat(property), owner);
            if (method != null) {
                getter = newMethodGetter(method, getter);
                owner = method.getReturnType();
            } else {
                Field field = getField(property, owner);
                if (field != null) {
                    getter = newFieldGetter(field, getter);
                    owner = field.getType();
                }
            }

            if (getter == null)
                throw new NoPropertyException("Type <"
                        + owner.getCanonicalName()
                        + "> doesn't have property <" + property + ">");

            if (mainGetter == null)
                mainGetter = getter;
        }

        return mainGetter;
    }

    private static AbstractGetter newMethodGetter(Method method,
            AbstractGetter parent) {
        AbstractGetter getter = new MethodGetter(method);
        if (parent != null)
            parent.setChild(getter);

        return getter;
    }

    private GetterFactory() {

    }
}
