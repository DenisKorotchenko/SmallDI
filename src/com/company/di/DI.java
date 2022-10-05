package com.company.di;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class DI {

    static Map<Class<?>, Object> createdSingletons = new HashMap<>();
    static Map<Class<?>, DIMetadata> registeredMetadata = new HashMap<>();



    public static Object init(Class<?> c) {
        for (Map.Entry<Class<?>, Object> e: createdSingletons.entrySet()) {
            if (c.isAssignableFrom(e.getKey())) {
                return e.getValue();
            }
        }
        for (Map.Entry<Class<?>, DIMetadata> e: registeredMetadata.entrySet()) {
            if (c.isAssignableFrom(e.getKey())) {
                try {
                    Object newEl = e.getValue().createNew();
                    if (e.getValue().elementType == DIElementType.SINGLETON) {
                        createdSingletons.put(c, newEl);
                    }
                    return newEl;
                } catch (Exception exception) {
                    continue;
                }
            }
        }
        throw new NoSuchElementException();
    }

    public static void register(Object o) {
        createdSingletons.put(o.getClass(), o);
    }

    public static DIMetadata createMetadata(Class<?> c, Object... params) {
        for (Constructor<?> constructor: c.getDeclaredConstructors()) {
            Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
            if (constructorParameterTypes.length != params.length) {
                continue;
            }
            boolean goodConstructor = true;

            for (int i = 0; i < params.length; i++) {
                if (!constructorParameterTypes[i].isAssignableFrom(params[i].getClass())) {
                    goodConstructor = false;
                    break;
                }
            }

            if (!goodConstructor) {
                continue;
            }

            DIMetadata diMetadata = new DIMetadata(
                    constructor,
                    params
            );
            return diMetadata;
        }
        return null;
    }

    public static boolean registerPrototype(Class<?> c, Object... params) {
        DIMetadata diMetadata = createMetadata(c, params);
        if (diMetadata != null) {
            diMetadata.elementType = DIElementType.PROTOTYPE;
            registeredMetadata.put(c, diMetadata);
            return true;
        }
        return false;
    }

    public static boolean registerSingleton(Class<?> c, Object... params) {
        DIMetadata diMetadata = createMetadata(c, params);
        if (diMetadata != null) {
            diMetadata.elementType = DIElementType.SINGLETON;
            registeredMetadata.put(c, diMetadata);
            return true;
        }
        return false;
    }
}
