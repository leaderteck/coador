package org.coador.jpa2;

import javax.persistence.Entity;

@Entity
public class E1Test {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
