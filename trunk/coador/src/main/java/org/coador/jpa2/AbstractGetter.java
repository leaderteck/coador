package org.coador.jpa2;

public abstract class AbstractGetter implements Getter {

    protected AbstractGetter child = null;

    @Override
    public <T> T get(Object target) {
        T value;
        try {
            value = this.<T> getValue(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (child != null)
            return child.get(value);

        return value;
    }

    protected abstract <T> T getValue(Object target) throws Exception;

    public void setChild(AbstractGetter child) {
        this.child = child;
    }

}
