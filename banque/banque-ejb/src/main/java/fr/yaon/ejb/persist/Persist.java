package fr.yaon.ejb.persist;

import fr.yaon.ejb.entity.Account;
import fr.yaon.ejb.entity.Transaction;

import java.util.Collection;

public interface Persist {
    void addAccount(Account account);
    void addTransaction(Transaction transaction);

    void removeAccount(Account account);
    void removeTransaction(Transaction transaction);

    Collection<Account> listAllAccounts();
    Account getAccount(long id);
    long getBalance(Account account);

    Collection<Transaction> listAllTransactions();
    Collection<Transaction> listLastTransactions(int limit);
    Collection<Transaction> findTransactions(Account account);
    Transaction getTransaction(long id);
}
