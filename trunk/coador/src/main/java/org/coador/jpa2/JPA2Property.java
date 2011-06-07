package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.coador.Property;

public class JPA2Property<Type> extends JPA2Operand implements Property<Type> {

    private Path<Object> path;
    private String propertyName;

    public JPA2Property(String propertyName, Path<Object> path) {
        this.path = path;
        this.propertyName = propertyName;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        // TODO Auto-generated method stub
        return path;
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        return path;
    }

    public String getName() {
        return propertyName;
    }

    public Path<?> getPath() {
        return path;
    }

    public Class<?> getOwnerClass() {
        Path<?> parent = path.getParentPath();
        if (parent == null)
            parent = path;
        else {
            while (parent.getParentPath() != null)
                parent = parent.getParentPath();
        }
        return parent.getModel().getBindableJavaType();
    }
}
