package org.coador.jpa2;

import javax.persistence.EntityManager;

public class JPA2TestHelperData {

    public static void store(EntityManager em) {
        em.getTransaction().begin();

        for (int i = 1; i < 1000; i++) {
            E1Test e = new E1Test();
            e.setName("John " + i);
            e.setYear(i);
            e.setE2(new E2Test());
            em.persist(e);
        }
        em.getTransaction().commit();
    }

}
