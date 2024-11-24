package fr.yaon.ejb.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {
    private long id;

    public Account(){
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }
}
