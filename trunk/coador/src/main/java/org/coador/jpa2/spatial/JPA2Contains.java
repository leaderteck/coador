package org.coador.jpa2.spatial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Criterion;
import org.coador.Operand;
import org.coador.jpa2.JPA2Criterion;
import org.coador.jpa2.JPA2Operand;

public class JPA2Contains extends JPA2Criterion implements Criterion {

    private static final String ST_CONTAINS = "ST_Contains";
    private JPA2Operand operand;
    private JPA2SpatialOperand o1;

    public JPA2Contains(JPA2SpatialOperand o1, Operand o2) {
        this.o1 = o1;
        this.operand = (JPA2Operand) o2;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, Root<?> root) {
        Expression<Boolean> p = cb.function(ST_CONTAINS, Boolean.class,
                o1.getExpression(cb), operand.getExpression(cb));
        return cb.isTrue(p);
    }
}
