package fr.yaon.ejb.init;

import fr.yaon.ejb.entity.Account;
import fr.yaon.ejb.entity.Transaction;
import fr.yaon.ejb.persist.LocalPersist;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.Collection;

@Stateless(mappedName = "myInitializerBean")
@Remote(Initializer.class)
public class InitializerBean implements Initializer {
    @EJB
    private LocalPersist localPersist;

    public void deleteEntities() {
        Collection<Account> previousAccounts = localPersist.listAllAccounts();
        Collection<Transaction> previousTransactions = localPersist.listAllTransactions();
        for (Transaction transaction : previousTransactions) {
            localPersist.removeTransaction(transaction);
        }

        for (Account account : previousAccounts) {
            localPersist.removeAccount(account);
        }
    }

    public void initializeEntities() {
        Collection<Account> previousAccounts = localPersist.listAllAccounts();
        Collection<Transaction> previousTransactions = localPersist.listAllTransactions();
        if (previousAccounts.isEmpty() && previousTransactions.isEmpty()) {
            Account account1 = new Account();
            Account account2 = new Account();
            localPersist.addAccount(account1);
            localPersist.addAccount(account2);

            Transaction t1 = new Transaction();
            t1.setDestination(account1);
            t1.setBalance(20000);
            localPersist.addTransaction(t1);

            Transaction t2 = new Transaction();
            t2.setSource(account1);
            t2.setDestination(account2);
            t2.setBalance(5000);
            localPersist.addTransaction(t2);
        }
    }
}
