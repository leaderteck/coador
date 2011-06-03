package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Operand;

public class JPA2NEQCriterion extends JPA2Criterion {

    private JPA2Operand o1;
    private JPA2Operand o2;

    public JPA2NEQCriterion(Operand o1, Operand o2) {
        this.o1 = (JPA2Operand) o1;
        this.o2 = (JPA2Operand) o2;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, Root<?> root) {
        return cb.notEqual(o1.getExpression(cb), o2.getExpression(cb));
    }

}
