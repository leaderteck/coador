package org.coador.jpa2;

import javax.persistence.EntityManager;

import org.coador.Criterion;
import org.coador.Disjunction;
import org.coador.Operand;
import org.coador.Restrictions;

public class JPA2Restrictions implements Restrictions {

    public JPA2Restrictions(EntityManager entityManager, Class<?> clazz) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Criterion eq(Operand op1, Operand op2) {
        return new JPA2EQCriterion(op1, op2);
    }

    @Override
    public Disjunction disjunction() {
        return new JPA2Disjunction();
    }

}
