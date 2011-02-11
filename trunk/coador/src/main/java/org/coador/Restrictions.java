package org.coador;

public interface Restrictions {

    Conjunction conjunction();

    Disjunction disjunction();

    Criterion eq(Operand o1, Operand o2);

    Criterion like(Operand o1, Operand o2);

    Criterion ilike(Operand o1, Operand o2);

}
