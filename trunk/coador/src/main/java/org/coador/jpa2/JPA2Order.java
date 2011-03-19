package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;

import org.coador.Operand;
import org.coador.Order;

public class JPA2Order implements Order {

    private JPA2Operand o;
    private boolean asc;

    public JPA2Order(Operand operand, boolean asc) {
        this.o = (JPA2Operand) operand;
        this.asc = asc;
    }

    public javax.persistence.criteria.Order getJPA2Order(CriteriaBuilder cb) {
        return asc ? cb.asc(o.getExpression(cb)) : cb.desc(o.getExpression(cb));
    }
}
