package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.coador.Operand;

public class JPA2Path<Type> extends JPA2Operand {

    private Path<Type> path;

    public JPA2Path(Path<Type> path) {
        this.path = path;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        return path;
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        throw new UnsupportedOperationException();
    }

}
