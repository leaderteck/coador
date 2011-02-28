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

    protected EntityManager entityManager;
    protected Class<T> clazz;
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

    public List<T> list() {
        TypedQuery<T> query = createJPAQuery();
        return query.getResultList();
    }

    private TypedQuery<T> createJPAQuery() {
        List<Predicate> p = createPredicates();
        criteria.where(p.toArray(new Predicate[p.size()]));
        return entityManager.createQuery(criteria);
    }

    private List<Predicate> createPredicates() {
        List<Predicate> ps = new ArrayList<Predicate>(criterionList.size());
        for (JPA2Criterion criterion : criterionList) {
            ps.add(criterion.predicate(cb));
        }
        return ps;
    }

    public <Type> Literal<Type> literal(Type value) {
        return new JPA2Literal<Type>(value);
    }

    @Override
    public <Type> Property<Type> property(String propertyName) {
        return new JPA2Property<Type>(propertyName, root.get(propertyName));
    }

}
