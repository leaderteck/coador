package org.coador.jpa2;

import javax.persistence.EntityManager;

import org.coador.CoadorFactory;
import org.coador.Criteria;

public class JPA2CoadorFactory implements CoadorFactory {

    private EntityManager entityManager;

    public JPA2CoadorFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> Criteria<T> createCriteria(Class<T> clazz) {
        return new JPA2Criteria<T>(entityManager, clazz);
    }

}
