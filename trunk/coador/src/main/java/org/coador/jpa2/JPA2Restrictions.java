package org.coador.jpa2;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.coador.Conjunction;
import org.coador.Criterion;
import org.coador.Disjunction;
import org.coador.Join;
import org.coador.Operand;
import org.coador.Property;
import org.coador.Restrictions;
import org.coador.TimePeriod;

public class JPA2Restrictions implements Restrictions {

    public JPA2Restrictions(EntityManager entityManager, Class<?> clazz) {
    }

    @Override
    public Criterion eq(Operand op1, Operand op2) {
        return new JPA2EQCriterion(op1, op2);
    }

    @Override
    public Disjunction disjunction() {
        return new JPA2Disjunction();
    }

    @Override
    public Conjunction conjunction() {
        return new JPA2Conjuction();
    }

    @Override
    public Criterion like(Operand o1, Operand o2) {
        return new JPA2LikeCriterion(o1, o2, true);
    }

    @Override
    public Criterion ilike(Operand o1, Operand o2) {
        return new JPA2LikeCriterion(o1, o2, false);
    }

    @Override
    public Criterion le(Operand o1, Operand o2) {
        return new JPA2LECriterion(o1, o2);
    }

    @Override
    public Criterion in(Operand o1, Object... values) {
        return new JPA2In(o1, values);
    }

    @Override
    public Criterion notIn(Operand o1, Object... values) {
        return new JPA2NotIn(o1, values);
    }

    @Override
    public Criterion neq(Operand o1, Operand o2) {
        return new JPA2NEQCriterion(o1, o2);
    }

    @Override
    public Criterion ge(Operand o1, Operand o2) {
        return new JPA2GECriterion(o1, o2);
    }

    @Override
    public Criterion intersects(Operand o1, TimePeriod period) {
        return new JPA2TemporalOverlaps(o1, period);
    }

    @Override
    public Criterion intersects(Property<? extends Collection<?>> property,
            Collection<?> collection) {
        return new JPA2PropertyIntersection(property, collection);
    }

    @Override
    public Join join(Property<?> property) {
        return new JPA2Join(property);
    }

}
