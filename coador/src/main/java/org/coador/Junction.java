package org.coador;

public interface Junction extends Criterion {
    Junction add(Criterion criterion);
}
