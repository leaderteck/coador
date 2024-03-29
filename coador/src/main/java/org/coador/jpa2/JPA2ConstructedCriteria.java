package org.coador.jpa2;

import java.util.List;

import org.coador.ConstructedCriteria;
import org.coador.Operand;

public class JPA2ConstructedCriteria<T, C> implements ConstructedCriteria<T, C> {

    private JPA2Criteria<C> criteria;
    @SuppressWarnings("unused")
    private Class<? extends C> targetClass;
    @SuppressWarnings("unused")
    private Operand[] operands;

    public JPA2ConstructedCriteria(JPA2Criteria<T> jpa2Criteria,
            Class<? extends C> construcClass, Operand... operands) {
        this.criteria = jpa2Criteria.newCriteriaObject(construcClass, operands);
        this.targetClass = construcClass;
        this.operands = operands;
    }

    @Override
    public ConstructedCriteria<T, C> setMaxResult(int max) {
        criteria.setMaxResult(max);
        return this;
    }

    public List<C> list() {
        return criteria.list();
    }

}
