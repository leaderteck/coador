package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.Operand;

public abstract class JPA2Operand implements Operand {

    public abstract <T> Expression<T> getExpression(CriteriaBuilder cb);

    public abstract <T> Expression<T> getLowerExpression(CriteriaBuilder cb);
}
