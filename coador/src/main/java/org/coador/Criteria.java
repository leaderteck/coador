package org.coador;

import java.util.List;

public interface Criteria<T> {

    Criteria<T> add(Criterion criterion);

    Restrictions getRestrictions();

    List<T> list();

    <Type> Literal<Type> literal(Type value);

    <Type> Property<Type> property(String propertyName);

}
