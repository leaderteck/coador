package org.coador.jpa2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class E3 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e3")
    @SequenceGenerator(name = "e3", sequenceName = "e3_id_seq")
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", name = " + name + "]";
    }
}
