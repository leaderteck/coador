package org.coador.jpa2.spatial;

import javax.persistence.EntityManager;

import org.coador.jpa2.JPA2Criteria;
import org.coador.spatial.SpatialCriteria;
import org.coador.spatial.SpatialRestrictions;

public class JPA2SpatialCriteria<T> extends JPA2Criteria<T> implements
        SpatialCriteria<T> {

    public JPA2SpatialCriteria(EntityManager entityManager, Class<T> clazz) {
        super(entityManager, clazz);
    }

    @Override
    public SpatialRestrictions getRestrictions() {
        return new JPA2SpatialRestrictions(entityManager, clazz);
    }

}
