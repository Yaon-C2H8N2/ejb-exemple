package fr.yaon.ejb.persist;

import fr.yaon.ejb.entity.Account;
import fr.yaon.ejb.entity.Transaction;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Stateless
@Remote(RemotePersist.class)
@Local(LocalPersist.class)
public class PersistBean implements LocalPersist, RemotePersist {
    @PersistenceContext
    private EntityManager entityManager = null;

    @Override
    public void addAccount(Account account) {
        entityManager.persist(account);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public void removeAccount(Account account) {
        entityManager.remove(account);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        entityManager.remove(transaction);
    }

    @Override
    public Collection<Account> listAllAccounts() {
        Query query = entityManager.createQuery("SELECT a FROM Account a");
        return query.getResultList();
    }

    @Override
    public Account getAccount(long id) {
        Query query = entityManager.createQuery("SELECT a FROM Account a WHERE a.id = :id");
        query.setParameter("id", id);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            return (Account) results.get(0);
        } else return null;
    }

    @Override
    public long getBalance(Account account){
        Collection<Transaction> transactions = this.findTransactions(account);

        long result = 0;
        for(Transaction transaction : transactions) {
            boolean accountFoundInTransaction = false;
            if(transaction.getSource() == account) {
                result -= transaction.getBalance();
            }
            if(transaction.getDestination() == account) {
                result += transaction.getBalance();
            }
        }
        return result;
    }

    @Override
    public Collection<Transaction> listAllTransactions() {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t");
        return query.getResultList();
    }

    @Override
    public Collection<Transaction> listLastTransactions(int limit) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t ORDER BY t.date");
        return query.setMaxResults(limit).getResultList();
    }

    @Override
    public Collection<Transaction> findTransactions(Account account) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.source = :account OR t.destination = :account");
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Transaction getTransaction(long id) {
        Query query = entityManager.createQuery("SELECT t FROM Transaction t WHERE t.id = :id");
        query.setParameter("id", id);
        List results = query.getResultList();
        if (!results.isEmpty()) {
            return (Transaction) results.get(0);
        } else return null;
    }
}
