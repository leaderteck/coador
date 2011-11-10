package org.coador.jpa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Criterion;
import org.coador.Join;
import org.coador.Property;

public class JPA2Join implements Join {

    private JPA2Property<?> property;
    private List<JPA2Criterion> criterions = new LinkedList<JPA2Criterion>();

    public JPA2Join(Property<?> property) {
        this.property = (JPA2Property<?>) property;
    }

    @Override
    public <T> Property<T> property(String propertyName) {
        return new JPA2InProperty<T>(propertyName);
    }

    @Override
    public Join add(Criterion criterion) {
        criterions.add((JPA2Criterion) criterion);
        return this;
    }

    public javax.persistence.criteria.Join<?, ?> join(From<?, ?> from,
            String alias) {
        String[] ss = property.getName().split("\\.");
        javax.persistence.criteria.Join<?, ?> join = from.join(ss[0]);
        for (int i = 1; i < ss.length; i++)
            join = join.join(ss[i]);

        return (javax.persistence.criteria.Join<?, ?>) join.alias(alias);
    }

    public Predicate[] createPredicates(
            javax.persistence.criteria.Join<?, ?> join, CriteriaBuilder cb,
            Root<?> root) {
        List<Predicate> ps = new ArrayList<Predicate>(criterions.size());
        for (JPA2Criterion criterion : criterions)
            ps.add(criterion.predicate(cb, join));

        return ps.toArray(new Predicate[ps.size()]);
    }
}
