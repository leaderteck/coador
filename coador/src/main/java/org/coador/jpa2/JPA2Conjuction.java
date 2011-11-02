package org.coador.jpa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.coador.Conjunction;
import org.coador.Criterion;

public class JPA2Conjuction extends JPA2Criterion implements Conjunction {

    private List<JPA2Criterion> list = new LinkedList<JPA2Criterion>();

    @Override
    public Conjunction add(Criterion criterion) {
        if (criterion instanceof JPA2Criterion)
            list.add((JPA2Criterion) criterion);

        return this;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb, From<?, ?> root) {
        List<Predicate> pl = new ArrayList<Predicate>(list.size());
        for (JPA2Criterion c : list)
            pl.add(c.predicate(cb, root));

        return cb.and(pl.toArray(new Predicate[pl.size()]));
    }

}