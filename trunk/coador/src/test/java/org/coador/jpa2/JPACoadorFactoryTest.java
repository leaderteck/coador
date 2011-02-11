package org.coador.jpa2;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.coador.CoadorFactory;
import org.coador.Criteria;
import org.coador.Criterion;
import org.coador.Disjunction;
import org.coador.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPACoadorFactoryTest {

    private EntityManager entityManager;
    private CoadorFactory coadorFactory;

    @Before
    public void setUp() {
        entityManager = JPA2TestHelper.createEntityManager();
        coadorFactory = new JPA2CoadorFactory(entityManager);
    }

    @After
    public void setDown() {
        entityManager.close();
    }

    @Test
    public void testEq() {
        String name = "John 547";
        Criteria<E1Test> criteria = coadorFactory.createCriteria(E1Test.class);
        Restrictions restrictions = criteria.getRestrictions();
        criteria.add(restrictions.eq(criteria.property("name"),
                criteria.literal(name)));

        List<E1Test> list = criteria.list();
        Assert.assertFalse("O resultado não deveria estar vazio",
                list.isEmpty());
        for (E1Test e1 : list) {
            Assert.assertEquals(new String(name), e1.getName());
        }
    }

    @Test
    public void testOR() {
        String name1 = "John 548";
        String name2 = "John 600";

        Criteria<E1Test> criteria = coadorFactory.createCriteria(E1Test.class);
        Restrictions r = criteria.getRestrictions();
        Disjunction d = r.disjunction();
        d.add(r.eq(criteria.property("name"), criteria.literal(name1))).add(
                r.eq(criteria.property("name"), criteria.literal(name2)));

        List<E1Test> list = criteria.add(d).list();
        Assert.assertFalse("Não deveria estar vazio", list.isEmpty());
    }
}