package fr.yaon.ejb.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Transaction implements Serializable {
    private long id;
    private long balance;
    private Account source;
    private Account destination;

    public Transaction(){
        this.balance = 0;
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
}
