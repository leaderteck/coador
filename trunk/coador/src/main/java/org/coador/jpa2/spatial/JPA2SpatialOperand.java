package org.coador.jpa2.spatial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import org.coador.jpa2.JPA2Operand;

import com.vividsolutions.jts.geom.Geometry;

public class JPA2SpatialOperand extends JPA2Operand {

    private static final String ST_GEOMFROMTEXT = "ST_GeomFromText";
    protected Geometry geometry;

    public JPA2SpatialOperand(Geometry o1) {
        this.geometry = o1;
    }

    @Override
    public Expression<?> getExpression(CriteriaBuilder cb) {
        return cb.function(ST_GEOMFROMTEXT, Geometry.class,
                cb.literal(geometry.toText()), cb.literal(geometry.getSRID()));
        // return cb.literal("SRID=" + geometry.getSRID() + ";"
        // + geometry.toText());
    }

    @Override
    public Expression<?> getLowerExpression(CriteriaBuilder cb) {
        return null;
    }

}
