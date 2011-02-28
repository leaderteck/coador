package org.coador;


public interface Criteria<T> {

    Criteria<T> add(Criterion criterion);

    Restrictions getRestrictions();

    <Type> Literal<Type> literal(Type value);

    <Type> Property<Type> property(String propertyName);

}