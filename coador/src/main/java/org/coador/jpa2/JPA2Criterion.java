package org.coador.jpa2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.coador.Criterion;

public abstract class JPA2Criterion implements Criterion {

    public abstract Predicate predicate(CriteriaBuilder cb);

}
