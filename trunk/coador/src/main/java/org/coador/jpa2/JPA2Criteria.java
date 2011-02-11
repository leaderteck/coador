package org.coador.jpa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Criteria;
import org.coador.Criterion;
import org.coador.Literal;
import org.coador.Property;
import org.coador.Restrictions;

public class JPA2Criteria<T> implements Criteria<T> {

    private EntityManager entityManager;
    private Class<T> clazz;
    private List<JPA2Criterion> criterionList = new LinkedList<JPA2Criterion>();
    private CriteriaBuilder cb;
    private CriteriaQuery<T> criteria;
    private Root<T> root;

    public JPA2Criteria(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        cb = entityManager.getCriteriaBuilder();
        criteria = cb.createQuery(clazz);
        root = criteria.from(clazz);
    }

    @Override
    public Criteria<T> add(Criterion criterion) {
        if (criterion instanceof JPA2Criterion)
            criterionList.add((JPA2Criterion) criterion);

        return this;
    }

    @Override
    public Restrictions getRestrictions() {
        return new JPA2Restrictions(entityManager, clazz);
    }

    @Override
    public List<T> list() {
        TypedQuery<T> query = createJPAQuery();
        return query.getResultList();
    }

    private TypedQuery<T> createJPAQuery() {
        predicates();
        return entityManager.createQuery(criteria);
    }

    private void predicates() {
        List<Predicate> ps = new ArrayList<Predicate>(criterionList.size());
        for (JPA2Criterion criterion : criterionList) {
            ps.add(criterion.predicate(cb));
        }

        criteria.where(ps.toArray(new Predicate[ps.size()]));
    }

    public <Type> Literal<Type> literal(Type value) {
        return new JPA2Literal<Type>(cb.literal(value));
    }

    @Override
    public <Type> Property<Type> property(String propertyName) {
        return new JPA2Property<Type>(propertyName, root.get(propertyName));
    }

}
