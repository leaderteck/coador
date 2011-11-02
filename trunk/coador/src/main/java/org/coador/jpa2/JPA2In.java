package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.coador.Operand;

public class JPA2In extends JPA2Criterion {

    private JPA2Operand o1;
    private Object[] values;

    public JPA2In(Operand o1, Object[] values) {
        this.o1 = (JPA2Operand) o1;
        this.values = values;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, From<?, ?> from) {
        In<Object> in = cb.in(o1.getExpression(cb, from));
        for (Expression<Object> e : getExpressionValues(cb, from)) {
            in.value(e);
        }
        return in;
        // .in(getExpressionValues(cb));
    }

    @SuppressWarnings("unchecked")
    private Expression<Object>[] getExpressionValues(CriteriaBuilder cb,
            From<?, ?> from) {
        Expression<Object>[] result = new Expression[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof JPA2Operand)
                result[i] = (Expression<Object>) ((JPA2Operand) values[i])
                        .getExpression(cb, from);
            else
                result[i] = cb.literal(values[i]);
        }

        return result;
    }

}
