package org.coador;

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

    void setMaxResult(int limit);

}
