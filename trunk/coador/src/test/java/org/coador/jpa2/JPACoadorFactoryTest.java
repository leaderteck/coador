package org.coador.jpa2;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.coador.CoadorFactory;
import org.coador.Conjunction;
import org.coador.Criteria;
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
        Criteria<E1> criteria = coadorFactory.createCriteria(E1.class);
        Restrictions restrictions = criteria.getRestrictions();
        criteria.add(restrictions.eq(criteria.property("name"),
                criteria.literal(name)));

        List<E1> list = criteria.list();
        Assert.assertFalse("O resultado não deveria estar vazio",
                list.isEmpty());
        for (E1 e1 : list) {
            Assert.assertEquals(new String(name), e1.getName());
        }
    }

    @Test
    public void testOR() {
        String name1 = "John 548";
        String name2 = "John 600";

        Criteria<E1> criteria = coadorFactory.createCriteria(E1.class);
        Restrictions r = criteria.getRestrictions();
        Disjunction d = r.disjunction();
        d.add(r.eq(criteria.property("name"), criteria.literal(name1))).add(
                r.eq(criteria.property("name"), criteria.literal(name2)));

        List<E1> list = criteria.add(d).list();
        Assert.assertFalse("Não deveria estar vazio", list.isEmpty());
    }

    @Test
    public void testAND() {
        String name = "John 555";
        Integer year = 555;

        Criteria<E1> c = coadorFactory.createCriteria(E1.class);
        Restrictions r = c.getRestrictions();
        Conjunction cj = r.conjunction();

        cj.add(r.eq(c.property("name"), c.literal(name))).add(
                r.eq(c.property("year"), c.literal(year)));

        List<E1> list = c.add(cj).list();
        Assert.assertFalse("Não quero listas vazias", list.isEmpty());

        for (E1 e : list) {
            Assert.assertEquals(new String(name), e.getName());
            Assert.assertEquals(new Integer(year), e.getYear());
        }
    }

    @Test
    public void testLikeILike() {
        String name = "John 7%";
        Criteria<E1> c = coadorFactory.createCriteria(E1.class);
        Restrictions r = c.getRestrictions();

        c.add(r.like(c.property("name"), c.literal(name)));

        List<E1> list = c.list();

        Assert.assertEquals("Eu esperava algo diferente", 111, list.size());

        c = coadorFactory.createCriteria(E1.class);
        c.add(r.ilike(c.property("name"), c.literal(name = "JoHn 1%")));

        list = c.list();
        Assert.assertEquals("Eu esperava algo diferente", 111, list.size());

        c = coadorFactory.createCriteria(E1.class);
        c.add(r.like(c.property("name"), c.literal(name)));

        list = c.list();
        Assert.assertEquals("Aqui eu queria uma lista vazia", 0, list.size());
    }

    @Test
    public void testRel() {

        E2 e2 = entityManager.find(E2.class, 10);
        Criteria<E1> c = coadorFactory.createCriteria(E1.class);
        Restrictions r = c.getRestrictions();

        c.add(r.eq(c.property("e2"), c.literal(e2)));

        List<E1> list = c.list();

    }

    @Test
    public void testRelLikeAND() {
        E2 e2 = entityManager.find(E2.class, 5);
        Criteria<E1> c = coadorFactory.createCriteria(E1.class);
        Restrictions r = c.getRestrictions();

        String name = "John 5%";
        Conjunction cj = r.conjunction();
        cj.add(r.like(c.property("name"), c.literal(name)));
        cj.add(r.eq(c.property("e2"), c.literal(e2)));

        c.add(cj);

        List<E1> list = c.list();

        Assert.assertEquals("Epa!", 1, list.size());
    }
}