package org.coador.jpa2;

import java.util.List;

import javax.persistence.EntityManager;

import org.coador.CoadorPropertyFixer;
import org.coador.CoadorProvider;
import org.coador.Criteria;
import org.coador.jpa2.spatial.JPA2SpatialCriteria;
import org.coador.spatial.SpatialCriteria;

public class JPA2CoadorProvider implements CoadorProvider {

    private EntityManager entityManager;
    private CoadorPropertyFixer propertyFixer;

    public JPA2CoadorProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> Criteria<T> createCriteria(Class<T> clazz) {
        return new JPA2Criteria<T>(entityManager, clazz, propertyFixer);
    }

    @Override
    public <T> SpatialCriteria<T> createSpatialCriteria(Class<T> clazz) {
        return new JPA2SpatialCriteria<T>(entityManager, clazz, propertyFixer);
    }

    @Override
    public <T> List<T> list(Criteria<T> criteria) {
        return ((JPA2Criteria<T>) criteria).list();
    }

    @Override
    public <T> T singleResult(Criteria<T> criteria) {
        return ((JPA2Criteria<T>) criteria).singleResult();
    }

    @Override
    public void setPropertyFixer(CoadorPropertyFixer propertyFixer) {
        if (propertyFixer != null)
            this.propertyFixer = propertyFixer;

    }

}
