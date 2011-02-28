package org.coador.jpa2.spatial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.coador.Criterion;
import org.coador.Operand;
import org.coador.jpa2.JPA2Criterion;
import org.coador.jpa2.JPA2Operand;

public class JPA2Intersects extends JPA2Criterion implements Criterion {

    private JPASpatialOperand o1;
    private JPA2Operand o2;

    public JPA2Intersects(JPASpatialOperand o1, Operand o2) {
        this.o1 = o1;
        this.o2 = (JPA2Operand) o2;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb) {
        Expression<Boolean> e = cb.function("ST_Contains", Boolean.class,
                o1.getExpression(cb), o2.getExpression(cb));
        return cb.isTrue(e);
    }
}
