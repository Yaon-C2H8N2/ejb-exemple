package fr.yaon.ejb.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Transaction implements Serializable {
    private long id;
    private long balance;
    private Account source;
    private Account destination;
    private Date date;

    public Transaction(){
        this.balance = 0;
        this.date = new Date(new java.util.Date().getTime());
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    public Account getSource(){
        return this.source;
    }

    public void setSource(Account account){
        this.source = account;
    }

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    public Account getDestination(){
        return this.destination;
    }

    public void setDestination(Account account){
        this.destination = account;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}
