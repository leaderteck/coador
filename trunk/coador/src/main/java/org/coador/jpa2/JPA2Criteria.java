package org.coador.jpa2;

import java.util.ArrayList;
import java.util.Collections;
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
import org.coador.Operand;
import org.coador.Order;
import org.coador.Property;
import org.coador.Restrictions;

public class JPA2Criteria<T> implements Criteria<T> {

    private CriteriaBuilder cb;
    protected Class<T> clazz;
    private CriteriaQuery<T> criteria;
    private List<JPA2Criterion> criterionList = new LinkedList<JPA2Criterion>();
    protected EntityManager entityManager;
    private List<JPA2Order> orderList = new LinkedList<JPA2Order>();
    private JPA2Restrictions restrictions;
    private Root<T> root;

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
    public Criteria<T> add(Order order) {
        if (order instanceof JPA2Order)
            orderList.add((JPA2Order) order);

        return this;
    }

    @Override
    public Order asc(Operand operand) {
        return new JPA2Order(operand, true);
    }

    private TypedQuery<T> createJPAQuery() {
        List<Predicate> p = createPredicates();
        criteria.where(p.toArray(new Predicate[p.size()]));
        criteria.orderBy(getOrdersBy());
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

    @Override
    public Order desc(Operand operand) {
        return new JPA2Order(operand, false);
    }

    @Override
    public Operand element() {
        return new JPA2Path<T>(root);
    }

    private List<javax.persistence.criteria.Order> getOrdersBy() {
        if (orderList.isEmpty())
            return Collections.emptyList();

        List<javax.persistence.criteria.Order> result = new ArrayList<javax.persistence.criteria.Order>(
                orderList.size());

        for (JPA2Order order : orderList)
            result.add(order.getJPA2Order(cb));

        return result;
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

    public <Type> Literal<Type> literal(Type value) {
        return new JPA2Literal<Type>(value);
    }

    private Path<Object> navigate(String propertyName) {
        String[] ps = propertyName.split("\\.");

        Path<Object> t = root.get(ps[0]);
        for (int i = 1; i < ps.length; i++)
            t = t.get(ps[i]);

        return t;
    }

    @Override
    public <Type> Property<Type> property(String propertyName) {
        return new JPA2Property<Type>(propertyName, navigate(propertyName));
    }

    @Override
    public Criteria<T> remove(Criterion criterion) {
        criterionList.remove(criterion);
        return this;
    }

    public T singleResult() {
        TypedQuery<T> query = createJPAQuery();
        return query.getSingleResult();
    }

    @Override
    public Criteria<T> newCriteria() {
        JPA2Criteria<T> newC = newCriteriaObject();
        newC.criterionList.addAll(criterionList);
        newC.orderList.addAll(orderList);
        return newC;
    }

    protected JPA2Criteria<T> newCriteriaObject() {
        return new JPA2Criteria<T>(entityManager, clazz);
    }

}
