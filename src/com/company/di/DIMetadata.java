package com.company.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DIMetadata {
    Constructor<?> constructor;
    Object[] params;
    DIElementType elementType = DIElementType.UNKNOWN;

    DIMetadata(Constructor<?> constructor, Object... params) {
        this.constructor = constructor;
        this.constructor.setAccessible(true);
        this.params = params;
    }

    Object createNew() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return constructor.newInstance(
                this.params
        );
    }
}
