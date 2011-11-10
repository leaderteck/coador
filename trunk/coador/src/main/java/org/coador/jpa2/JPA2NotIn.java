package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.coador.Operand;

public class JPA2NotIn extends JPA2In {

    public JPA2NotIn(Operand o1, Object[] values) {
        super(o1, values);
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, From<?, ?> from) {
        return super.predicate(cb, from).not();
    }

}
