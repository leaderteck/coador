package org.coador;

public interface CoadorFactory {

    <T> Criteria<T> createCriteria(Class<T> clazz);
}
