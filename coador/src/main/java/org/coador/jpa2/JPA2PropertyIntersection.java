package org.coador.jpa2;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.coador.Property;

public class JPA2PropertyIntersection extends JPA2Criterion implements
        JPA2PostLoadFilter {

    private JPA2Property<Collection<?>> property;
    private Collection<?> collection;
    private Class<?> targetClass;
    private Getter getter;

    @SuppressWarnings("unchecked")
    public JPA2PropertyIntersection(Property<? extends Collection<?>> property,
            Collection<?> collection) {
        this.property = (JPA2Property<Collection<?>>) property;
        this.collection = collection;
        targetClass = this.property.getOwnerClass();
        this.getter = GetterFactory.newGetter(targetClass,
                this.property.getName());
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, Root<?> root) {
        return null;
    }

    @Override
    public boolean filter(Object object) {
        Collection<?> current = getter.<Collection<?>> get(object);

        for (Object value : collection) {
            if (current.contains(value))
                return true;
        }

        return false;
    }
}
