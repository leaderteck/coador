package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.coador.Operand;

public class JPA2EQCriterion extends JPA2Criterion {

    private JPA2Operand op1;
    private JPA2Operand op2;

    public JPA2EQCriterion(Operand op1, Operand op2) {
        this.op1 = (JPA2Operand) op1;
        this.op2 = (JPA2Operand) op2;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, From<?, ?> root) {
        return cb.equal(op1.getExpression(cb), op2.getExpression(cb));
    }

}
