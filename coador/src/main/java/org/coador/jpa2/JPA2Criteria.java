package org.coador.jpa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
    private JPA2Restrictions restrictions;

    public JPA2Criteria(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
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
        if (restrictions == null)
            restrictions = new JPA2Restrictions(entityManager, clazz);

        return restrictions;
    }

    public List<T> list() {
        TypedQuery<T> query = createJPAQuery();
        return query.getResultList();
    }

    private TypedQuery<T> createJPAQuery() {
        List<Predicate> p = createPredicates();
        criteria.where(p.toArray(new Predicate[p.size()]));
        try {
            return entityManager.createQuery(criteria);
        } finally {
            criteria = cb.createQuery(clazz);
            root = criteria.from(clazz);
        }
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
        return new JPA2Property<Type>(propertyName, navigate(propertyName));
    }

    private Path<Object> navigate(String propertyName) {
        String[] ps = propertyName.split("\\.");

        Path<Object> t = root.get(ps[0]);
        for (int i = 1; i < ps.length; i++)
            t = t.get(ps[i]);

        return t;
    }

    @Override
    public Criteria<T> remove(Criterion criterion) {
        criterionList.remove(criterion);
        return this;
    }

}
