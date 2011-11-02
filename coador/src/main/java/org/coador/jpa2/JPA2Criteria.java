package org.coador.jpa2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.coador.CoadorPropertyFixer;
import org.coador.ConstructedCriteria;
import org.coador.Criteria;
import org.coador.Criterion;
import org.coador.Join;
import org.coador.Literal;
import org.coador.Operand;
import org.coador.Order;
import org.coador.Property;
import org.coador.Restrictions;
import org.coador.TimePeriod;

public class JPA2Criteria<T> implements Criteria<T> {

    private CriteriaBuilder cb;
    private CriteriaQuery<T> criteria;
    private Deque<JPA2Criterion> criterionDeque = new LinkedList<JPA2Criterion>();
    protected EntityManager entityManager;
    private List<JPA2Join> joins = new LinkedList<JPA2Join>();
    private int limit = 0;
    private List<JPA2Order> orderList = new LinkedList<JPA2Order>();
    private List<JPA2PostLoadFilter> postLoadListeners = new LinkedList<JPA2PostLoadFilter>();
    protected CoadorPropertyFixer propertyFixer = CoadorPropertyFixer.NOP;
    protected JPA2Restrictions restrictions;
    protected Root<?> root;
    protected Class<?> sourceClass;
    protected Class<T> targetClass;

    private JPA2Criteria() {
        // TODO Auto-generated constructor stub
    }

    public JPA2Criteria(EntityManager entityManager, Class<T> targetClass,
            CoadorPropertyFixer propertyFixer) {
        this.entityManager = entityManager;
        this.sourceClass = this.targetClass = targetClass;
        if (propertyFixer != null)
            this.propertyFixer = propertyFixer;
        cb = entityManager.getCriteriaBuilder();
        criteria = cb.createQuery(targetClass);
        root = criteria.from(sourceClass);
        updateAlias();
    }

    @Override
    public Criteria<T> add(Criterion criterion) {
        if (criterion instanceof JPA2Criterion)
            criterionDeque.addFirst((JPA2Criterion) criterion);

        if (criterion instanceof JPA2PostLoadFilter)
            postLoadListeners.add((JPA2PostLoadFilter) criterion);

        return this;
    }

    @Override
    public void add(Join join) {
        joins.add((JPA2Join) join);
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

    @Override
    public <C> ConstructedCriteria<T, C> construct(
            Class<? extends C> construcClass, Operand... operand) {
        return new JPA2ConstructedCriteria<T, C>(this, construcClass, operand);
    }

    private TypedQuery<T> createJPAQuery() {
        List<Predicate> p = createPredicates();

        if (!joins.isEmpty()) {
            makeJoins(p);
            criteria.distinct(true);
        }

        criteria.where(p.toArray(new Predicate[p.size()]));
        criteria.orderBy(getOrdersBy());
        try {
            return entityManager.createQuery(criteria);
        } finally {
            criteria = cb.createQuery(targetClass);
            root = criteria.from(sourceClass);
            updateAlias();
        }
    }

    private void makeJoins(List<Predicate> predicates) {
        int i = 0;
        for (JPA2Join jpa2join : joins) {
            javax.persistence.criteria.Join<?, ?> join = jpa2join.join(root,
                    "join_" + (i++));
            Predicate[] ps = jpa2join.createPredicates(join, cb, root);
            for (Predicate p : ps)
                predicates.add(p);
        }
    }

    private List<Predicate> createPredicates() {
        List<Predicate> ps = new ArrayList<Predicate>(criterionDeque.size());
        Predicate p;
        for (JPA2Criterion criterion : criterionDeque) {
            p = criterion.predicate(cb, root);
            if (p != null)
                ps.add(p);
        }
        return ps;
    }

    @Override
    public Order desc(Operand operand) {
        return new JPA2Order(operand, false);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Operand element() {
        return new JPA2Path(root);
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
            restrictions = new JPA2Restrictions(entityManager, targetClass);

        return restrictions;
    }

    public List<T> list() {
        TypedQuery<T> query = createJPAQuery();
        if (limit > 0)
            query.setMaxResults(limit);

        List<T> result = query.getResultList();
        if (!postLoadListeners.isEmpty())
            postLoadProcess(result);

        return result;
    }

    public <Type> Literal<Type> literal(Type value) {
        return new JPA2Literal<Type>(value);
    }

    private Path<Object> navigate(String propertyName) {
        String[] ps = propertyName.split("\\.");
        Path<Object> t;

        String pN = propertyFixer.fixProperty(ps[0], root.getJavaType());
        if (pN != null && !pN.isEmpty())
            t = root.get(pN);
        else
            t = root.get(ps[0]);

        for (int i = 1; i < ps.length; i++) {
            pN = propertyFixer.fixProperty(ps[i], t.getJavaType());
            if (pN != null && !pN.isEmpty())
                t = t.get(pN);
            else
                t = t.get(ps[i]);
        }

        return t;
    }

    @Override
    public Criteria<T> newCriteria() {
        JPA2Criteria<T> newC = newCriteriaObject();
        newC.criterionDeque.addAll(criterionDeque);
        newC.orderList.addAll(orderList);
        newC.limit = limit;
        return newC;
    }

    protected JPA2Criteria<T> newCriteriaObject() {
        return new JPA2Criteria<T>(entityManager, targetClass, propertyFixer);
    }

    @SuppressWarnings("unchecked")
    protected <C> JPA2Criteria<C> newCriteriaObject(
            Class<? extends C> targetClass, Operand... operands) {

        ArrayList<Selection<?>> selections = new ArrayList<Selection<?>>(
                operands.length);
        for (Operand operand : operands)
            selections.add(((JPA2Operand) operand).getExpression(cb));

        CompoundSelection<? extends C> cc = cb.construct(targetClass,
                selections.toArray(new Selection[selections.size()]));

        JPA2Criteria<C> newC = new JPA2Criteria<C>();
        newC.criterionDeque = (criterionDeque);
        newC.orderList = (orderList);
        newC.limit = limit;
        newC.cb = cb;
        newC.criteria = ((CriteriaQuery<C>) cb.createQuery(targetClass))
                .select(cc);
        newC.entityManager = entityManager;
        newC.targetClass = (Class<C>) targetClass;
        newC.sourceClass = sourceClass;
        newC.root = newC.criteria.from(sourceClass);
        newC.postLoadListeners.addAll(postLoadListeners);
        newC.updateAlias();
        return newC;
    }

    @Override
    public TimePeriod period(Calendar dfI, Calendar dtf) {
        return new JPA2TimePeriod(dfI, dtf);
    }

    private void postLoadProcess(List<T> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T object = it.next();

            for (JPA2PostLoadFilter listener : postLoadListeners)
                if (!listener.filter(object))
                    it.remove();
        }
    }

    @Override
    public <Type> Property<Type> property(String propertyName) {
        return new JPA2Property<Type>(propertyName, navigate(propertyName));
    }

    @Override
    public Criteria<T> remove(Criterion criterion) {
        criterionDeque.remove(criterion);
        return this;
    }

    @Override
    public JPA2Criteria<T> setMaxResult(int limit) {
        this.limit = limit;
        return this;
    }

    public T singleResult() {
        TypedQuery<T> query = createJPAQuery();
        return query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    private void updateAlias() {
        root = (Root<T>) root.alias(sourceClass.getSimpleName().toLowerCase());
    }
}
