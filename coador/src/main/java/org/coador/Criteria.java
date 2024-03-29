package org.coador;

import java.util.Calendar;

public interface Criteria<T> extends Cloneable {

    Criteria<T> add(Criterion criterion);

    Criteria<T> add(Order order);

    Order asc(Operand operand);

    Order desc(Operand operand);

    Operand element();

    Restrictions getRestrictions();

    <Type> Literal<Type> literal(Type value);

    <Type> Property<Type> property(String propertyName);

    Criteria<T> remove(Criterion criterion);

    Criteria<T> newCriteria();

    Criteria<T> setMaxResult(int limit);

    TimePeriod period(Calendar cI, Calendar cF);

    <C> ConstructedCriteria<T, C> construct(Class<? extends C> construcClass,
            Operand... operand);

    void add(Join join);

    Operand count(Operand operand);
}
