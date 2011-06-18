package org.coador.util;

import java.lang.reflect.Method;


public class MethodGetter extends AbstractGetter {

    private Method method;

    public MethodGetter(Method method) {
        this.method = method;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T getValue(Object target) throws Exception {
        return (T) method.invoke(target);
    }

}
