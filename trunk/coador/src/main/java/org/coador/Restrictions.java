package org.coador;

import java.util.Collection;

public interface Restrictions {

    Conjunction conjunction();

    Disjunction disjunction();

    Criterion eq(Operand o1, Operand o2);

    Criterion ge(Operand o1, Operand o2);

    Criterion ilike(Operand o1, Operand o2);

    Criterion in(Operand o1, Object... values);

    Criterion notIn(Operand o1, Object... values);

    Criterion intersects(Operand o1, TimePeriod period);

    @Deprecated
    Criterion intersects(Property<? extends Collection<?>> property,
            Collection<?> collection);

    Join join(Property<?> property);

    Criterion le(Operand o1, Operand o2);

    Criterion like(Operand o1, Operand o2);

    Criterion neq(Operand o1, Operand o2);

}
