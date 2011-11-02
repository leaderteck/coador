package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

public class JPA2InProperty<T> extends JPA2Property<T> {

    private String propertyName;

    public JPA2InProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb, From<?, ?> from) {
        return from.get(propertyName);
    }
}
