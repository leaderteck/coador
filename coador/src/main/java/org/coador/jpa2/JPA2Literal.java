package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.Literal;

public class JPA2Literal<T> extends JPA2Operand implements Literal<T> {

    private T literal;

    public JPA2Literal(T literal) {
        this.literal = literal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Expression<E> getExpression(CriteriaBuilder cb) {
        return (Expression<E>) cb.literal(literal);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Expression<E> getLowerExpression(CriteriaBuilder cb) {
        return (Expression<E>) cb.literal(literal.toString().toLowerCase());
    }

}
