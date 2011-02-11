package org.coador;

public interface Restrictions {

    Criterion eq(Operand op1, Operand op2);

    Disjunction disjunction();

}
