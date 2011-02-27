package org.coador.jpa2;

import javax.persistence.EntityManager;

import org.coador.CoadorProvider;
import org.coador.Criteria;
import org.coador.jpa2.spatial.JPA2SpatialCriteria;
import org.coador.spatial.SpatialCriteria;

public class JPA2CoadorProvider implements CoadorProvider {

    private EntityManager entityManager;

    public JPA2CoadorProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> Criteria<T> createCriteria(Class<T> clazz) {
        return new JPA2Criteria<T>(entityManager, clazz);
    }

    @Override
    public <T> SpatialCriteria<T> createSpatialCriteria(Class<T> clazz) {
        return new JPA2SpatialCriteria<T>(entityManager, clazz);
    }

}
