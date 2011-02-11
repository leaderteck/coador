package org.coador.jpa2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.coador.Criterion;
import org.coador.Disjunction;

public class JPA2Disjunction extends JPA2Criterion implements Disjunction {

    List<JPA2Criterion> criterionList = new LinkedList<JPA2Criterion>();

    @Override
    public Disjunction add(Criterion criterion) {
        if (criterion instanceof JPA2Criterion)
            criterionList.add((JPA2Criterion) criterion);

        return this;
    }

    @Override
    public Predicate predicate(CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>(criterionList.size());
        for (JPA2Criterion jpa2c : criterionList)
            list.add(jpa2c.predicate(cb));

        return cb.or(list.toArray(new Predicate[list.size()]));
    }

}
