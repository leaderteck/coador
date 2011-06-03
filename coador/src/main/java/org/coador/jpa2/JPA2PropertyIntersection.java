package org.coador.jpa2;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.coador.Property;

public class JPA2PropertyIntersection extends JPA2Criterion {

    private JPA2Property<Collection<?>> property;
    private Collection<?> collection;

    @SuppressWarnings("unchecked")
    public JPA2PropertyIntersection(Property<? extends Collection<?>> property,
            Collection<?> collection) {
        this.property = (JPA2Property<Collection<?>>) property;
        this.collection = collection;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, Root<?> root) {
        Join<?, ?> join = findJoin(root, property.getName().split("\\."));
        return join.in(cb.literal(collection));
    }

    @SuppressWarnings({ "rawtypes" })
    private Join<?, ?> findJoin(Root<?> root, String[] strings) {

        ManagedType<?> type = root.getModel();
        Attribute<?, ?> attribute = type.getAttribute(strings[0]);

        Join join = makeJoin(root, attribute);

        for (int i = 1; i < strings.length; i++) {
            if (attribute.isCollection()) {
                // TODO: ????
            } else if (attribute.isAssociation()) {
                // TODO: ????
            } else {
                switch (attribute.getPersistentAttributeType()) {
                case BASIC:
                    break;
                default:
                    SingularAttribute<?, ?> s = (SingularAttribute<?, ?>) attribute;
                    type = (ManagedType<?>) s.getType();
                    attribute = type.getAttribute(strings[i]);
                    join = makeJoin(join, attribute);
                }
            }
        }

        return join;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Join makeJoin(From<?, ?> from, Attribute<?, ?> attribute) {
        if (attribute.isCollection()) {
            if (CollectionAttribute.class
                    .isAssignableFrom(attribute.getClass()))
                return from.join((CollectionAttribute) attribute);
            if (SetAttribute.class.isAssignableFrom(attribute.getClass()))
                return from.join((SetAttribute) attribute);
            if (ListAttribute.class.isAssignableFrom(attribute.getClass()))
                return from.join((ListAttribute) attribute);
            if (MapAttribute.class.isAssignableFrom(attribute.getClass()))
                return from.join((MapAttribute) attribute);
        }
        return from.join((SingularAttribute) attribute);
    }

}
