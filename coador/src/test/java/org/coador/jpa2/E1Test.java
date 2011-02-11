package org.coador.jpa2;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class E1Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e1")
    @SequenceGenerator(name = "e1", sequenceName = "e1_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private E2Test e2;

    public Integer getId() {
        return id;
    }

    private String name;
    private Integer year;

    public E2Test getE2() {
        return e2;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public void setE2(E2Test e2) {
        this.e2 = e2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
