package org.coador;

import org.coador.spatial.SpatialCriteria;

public interface CoadorProvider {

    <T> Criteria<T> createCriteria(Class<T> clazz);

    <T> SpatialCriteria<T> createSpatialCriteria(Class<T> clazz);
}
