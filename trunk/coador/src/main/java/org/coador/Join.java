package org.coador;

public interface Join {

    <T> Property<T> property(String propertyName);

    Join add(Criterion criterion);

}
