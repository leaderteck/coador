package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;

import org.coador.Operand;

public abstract class JPA2Operand implements Operand {

    public abstract Expression<?> getExpression(CriteriaBuilder cb);

    public Expression<?> getExpression(CriteriaBuilder cb, From<?, ?> from) {
        throw new UnsupportedOperationException();
    }

    public abstract Expression<?> getLowerExpression(CriteriaBuilder cb);

}
