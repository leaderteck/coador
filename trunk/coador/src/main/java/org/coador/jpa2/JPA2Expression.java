package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class JPA2Expression extends JPA2Operand {

    private Expression<?> expression;

    public JPA2Expression(Expression<?> expression) {
        this.expression = expression;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        return expression;
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        throw new UnsupportedOperationException();
    }

}
