package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.coador.Criterion;
import org.coador.Operand;

public class JPA2LECriterion extends JPA2Criterion implements Criterion {

    private JPA2Operand o1;
    private JPA2Operand o2;

    public JPA2LECriterion(Operand o1, Operand o2) {
        this.o1 = (JPA2Operand) o1;
        this.o2 = (JPA2Operand) o2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate predicate(CriteriaBuilder cb, From<?, ?> root) {
        return cb.lessThanOrEqualTo(
                (Expression<Comparable<Object>>) o1.getExpression(cb),
                (Expression<Comparable<Object>>) o2.getExpression(cb));
    }

}
