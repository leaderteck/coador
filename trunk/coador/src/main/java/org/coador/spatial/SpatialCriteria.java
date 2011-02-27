package org.coador.spatial;

import org.coador.Criteria;

public interface SpatialCriteria<T> extends Criteria<T> {

    SpatialRestrictions getRestrictions();
}
