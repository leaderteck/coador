package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.coador.Operand;

public class JPA2LikeCriterion extends JPA2Criterion {

    private JPA2Operand o1;
    private JPA2Operand o2;
    private boolean sensitive;

    public JPA2LikeCriterion(Operand o1, Operand o2, boolean sensitive) {
        this.o1 = (JPA2Operand) o1;
        this.o2 = (JPA2Operand) o2;
        this.sensitive = sensitive;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb) {
        if (sensitive)
            return sensitivePredicate(cb);

        return insensitivePredicate(cb);
    }

    private Predicate insensitivePredicate(CriteriaBuilder cb) {
        Expression<String> l1 = cb.lower(o1.<String> getExpression(cb));

        return cb.like(l1, o2.<String> getLowerExpression(cb));
    }

    private Predicate sensitivePredicate(CriteriaBuilder cb) {
        return cb.like(o1.<String> getExpression(cb),
                o2.<String> getExpression(cb));
    }

}
