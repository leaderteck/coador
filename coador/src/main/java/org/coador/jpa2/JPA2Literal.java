package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.Literal;

public class JPA2Literal<T> extends JPA2Operand implements Literal<T> {

    private T literal;

    public JPA2Literal(T literal) {
        this.literal = literal;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        return cb.literal(literal);
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        return cb.literal(literal.toString().toLowerCase());
    }

}
