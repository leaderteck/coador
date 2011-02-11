package org.coador.jpa2;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.coador.Property;

public class JPA2Property<Type> extends JPA2Operand implements Property<Type> {

    private Path<Object> path;

    public JPA2Property(String propertyName, Path<Object> path) {
        this.path = path;
    }

    @Override
    public Expression<?> getExpression() {
        return path;
    }

}
