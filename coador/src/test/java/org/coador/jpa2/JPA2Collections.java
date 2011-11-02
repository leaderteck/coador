package org.coador.jpa2;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.coador.Criteria;
import org.coador.Join;
import org.coador.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPA2Collections {

    private JPA2CoadorProvider provider;
    private EntityManager em;

    @Before
    public void setUp() {
        em = JPA2TestHelper.createEntityManager();
        JPA2TestHelperData.store(em);
        provider = new JPA2CoadorProvider(em);
    }

    @Test
    public void intersectsJPQL() {

        TypedQuery<E1> query = em
                .createQuery(
                        "SELECT DISTINCT e1 FROM E1 e1 JOIN e1.e3 c  WHERE c.name = :l",
                        E1.class);

        query.setParameter("l", "e3 2");
        List<E1> result = query.getResultList();
        System.out.println(result.size());
    }

    @Test
    public void intersects() {
        // Yeahhh
        Criteria<E1> c1 = provider.createCriteria(E1.class);
        Restrictions r1 = c1.getRestrictions();
        Join je3 = r1.join(c1.property("e3"));
        c1.add(je3);
        je3.add(r1.in(je3.property("name"), new Object[] { "e3 3" }));
        List<E1> list = provider.list(c1);
        System.out.println(list.size());
        for (E1 e1 : list) {
            System.out.println(e1.getE3());
        }
    }

    @After
    public void setDown() {
        em.close();
    }
}
