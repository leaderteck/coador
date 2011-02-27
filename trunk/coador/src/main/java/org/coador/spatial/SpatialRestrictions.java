package org.coador.spatial;

import org.coador.Criterion;
import org.coador.Operand;
import org.coador.Restrictions;

import com.vividsolutions.jts.geom.Geometry;

public interface SpatialRestrictions extends Restrictions {

    Criterion contains(Geometry o1, Operand o2);

}
