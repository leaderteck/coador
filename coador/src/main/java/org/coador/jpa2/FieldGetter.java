package org.coador.jpa2;

import java.lang.reflect.Field;

public class FieldGetter extends AbstractGetter {

    private Field field;

    public FieldGetter(Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T getValue(Object target) throws Exception {
        return (T) field.get(target);
    }

}
