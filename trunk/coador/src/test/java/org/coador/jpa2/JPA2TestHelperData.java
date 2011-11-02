package org.coador.jpa2;

import java.util.ArrayList;

import javax.persistence.EntityManager;

public class JPA2TestHelperData {

    public static void store(EntityManager em) {
        em.getTransaction().begin();

        for (int i = 1; i < 1000; i++) {
            E1 e = new E1();
            e.setName("John " + i);
            e.setYear(i);
            e.setE2(new E2());
            ArrayList<E3> l = new ArrayList<E3>();
            for (int i3 = 0; i3 < 4; i3++) {
                E3 e3 = new E3();
                e3.setName("e3 " + (int) (Math.random() * 5));
                l.add(e3);
            }
            e.setE3(l);
            em.persist(e);
        }
        em.getTransaction().commit();
    }

}
