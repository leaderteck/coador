package org.coador.jpa2.spatial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.jpa2.JPA2Operand;

import com.vividsolutions.jts.geom.Geometry;

public class JPASpatialOperand extends JPA2Operand {

    protected Geometry geometry;

    public JPASpatialOperand(Geometry o1) {
        this.geometry = o1;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        return cb.literal("SRID=" + geometry.getSRID() + ";"
                + geometry.toText());
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        return null;
    }

}
