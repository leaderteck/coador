package org.coador.jpa2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class E2Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e2")
    @SequenceGenerator(name = "e2", sequenceName = "e2_id_seq")
    private Integer id;

    public Integer getId() {
        return id;
    }
}
