package org.coador.jpa2.spatial;

import javax.persistence.EntityManager;

import org.coador.Criterion;
import org.coador.Operand;
import org.coador.jpa2.JPA2Restrictions;
import org.coador.spatial.SpatialRestrictions;

import com.vividsolutions.jts.geom.Geometry;

public class JPA2SpatialRestrictions extends JPA2Restrictions implements
        SpatialRestrictions {

    public JPA2SpatialRestrictions(EntityManager entityManager, Class<?> clazz) {
        super(entityManager, clazz);
    }

    @Override
    public Criterion contains(Geometry o1, Operand o2) {
        return new JPA2Contains(new JPASpatialOperand(o1), o2);
    }

    @Override
    public Criterion intersects(Geometry o1, Operand o2) {
        return new JPA2Intersects(new JPASpatialOperand(o1), o2);
    }

}
