package org.coador.jpa2.spatial;

import javax.persistence.EntityManager;

import org.coador.CoadorPropertyFixer;
import org.coador.jpa2.JPA2Criteria;
import org.coador.spatial.SpatialCriteria;
import org.coador.spatial.SpatialRestrictions;

public class JPA2SpatialCriteria<T> extends JPA2Criteria<T> implements
        SpatialCriteria<T> {

    public JPA2SpatialCriteria(EntityManager entityManager, Class<T> clazz,
            CoadorPropertyFixer propertyFixer) {
        super(entityManager, clazz, propertyFixer);
    }

    @Override
    public SpatialRestrictions getRestrictions() {
        if (restrictions == null)
            restrictions = new JPA2SpatialRestrictions(entityManager, clazz);

        return (SpatialRestrictions) restrictions;
    }

    public SpatialCriteria<T> newCriteria() {
        return (SpatialCriteria<T>) super.newCriteria();
    }

    @Override
    protected JPA2Criteria<T> newCriteriaObject() {
        return new JPA2SpatialCriteria<T>(entityManager, clazz, propertyFixer);
    }

}
