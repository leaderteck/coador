package org.coador.jpa2;

import javax.persistence.criteria.Expression;

import org.coador.Literal;

public class JPA2Literal<T> extends JPA2Operand implements Literal<T> {

    private Expression<T> literal;

    public JPA2Literal(Expression<T> literal) {
        this.literal = literal;
    }

    @Override
    public Expression<?> getExpression() {
        return literal;
    }

}
