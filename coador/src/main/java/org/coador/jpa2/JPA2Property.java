package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.coador.Property;

public class JPA2Property<Type> extends JPA2Operand implements Property<Type> {

    private Path<Object> path;

    public JPA2Property(String propertyName, Path<Object> path) {
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Expression<T> getExpression(CriteriaBuilder cb) {
        // TODO Auto-generated method stub
        return (Expression<T>) path;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Expression<T> getLowerExpression(CriteriaBuilder cb) {
        return (Expression<T>) path;
    }

}
