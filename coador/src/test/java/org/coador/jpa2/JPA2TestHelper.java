package org.coador.jpa2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JPA2TestHelper {

    private static boolean loaded = false;

    public static EntityManager createEntityManager() {
        return commit(Persistence.createEntityManagerFactory("CoadorJPA2Test")
                .createEntityManager());
    }

    private synchronized static EntityManager commit(EntityManager em) {
        if (loaded)
            return em;

        em.getTransaction().begin();
        for (int i = 1; i < 100; i++) {
            String sql = loadSQL(i);
            if (sql == null)
                break;

            Query query = em.createNativeQuery(sql);
            query.executeUpdate();

        }
        try {
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loaded = true;
        JPA2TestHelperData.store(em);
        return em;
    }

    private static String loadSQL(int i) {

        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("ddl" + i + ".sql");

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder strbuilder = new StringBuilder();
            while (reader.ready()) {
                strbuilder.append(reader.readLine());
                if (reader.ready())
                    strbuilder.append('\n');
            }

            reader.close();
            return strbuilder.toString();
        } catch (Exception e) {
            return null;
        }

    }
}
