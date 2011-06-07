package org.coador.jpa2;

public interface JPA2PostLoadFilter {

    boolean filter(Object object);

}
