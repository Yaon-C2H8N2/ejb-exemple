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
            Account[] accounts = new Account[100];
            for(int i = 0; i<100; i++) {
                accounts[i] = new Account();
                localPersist.addAccount(accounts[i]);
            }

            for(Account account : accounts){
                int nbTransaction = (int)(1 + Math.random() * 10);
                for (int i = 0; i<nbTransaction; i++){
                    int targetAccount = 0;
                    do{
                        targetAccount = (int)(Math.random() * accounts.length);
                    }while(accounts[targetAccount].getId() == account.getId());
                    long transactionAmount = (long)(1 + Math.random() * 100000);

                    Transaction transaction = new Transaction();
                    transaction.setSource(account);
                    transaction.setDestination(accounts[i]);
                    transaction.setBalance(transactionAmount);

                    localPersist.addTransaction(transaction);
                }
            }

            previousAccounts = localPersist.listAllAccounts();
            previousTransactions = localPersist.listAllTransactions();

            System.out.println("Initialized "+previousAccounts.size()+" accounts and "+previousTransactions.size()+" transactions.");
        }
    }
}
