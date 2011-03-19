package org.coador;

import java.util.List;

import org.coador.spatial.SpatialCriteria;

public interface CoadorProvider {

    <T> Criteria<T> createCriteria(Class<T> clazz);

    <T> SpatialCriteria<T> createSpatialCriteria(Class<T> clazz);

    <T> List<T> list(Criteria<T> criteria);

    <T> T singleResult(Criteria<T> criteria);
}
