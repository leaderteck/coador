package org.coador;

public interface Restrictions {

    Conjunction conjunction();

    Disjunction disjunction();

    Criterion eq(Operand o1, Operand o2);

    Criterion ge(Operand o1, Operand o2);

    Criterion ilike(Operand o1, Operand o2);

    Criterion in(Operand o1, Object... values);

    Criterion intersects(Operand o1, TimePeriod period);

    Criterion le(Operand o1, Operand o2);

    Criterion like(Operand o1, Operand o2);

    Criterion neq(Operand o1, Operand o2);

}
