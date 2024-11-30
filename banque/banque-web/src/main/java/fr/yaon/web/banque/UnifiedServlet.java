package fr.yaon.web.banque;

import fr.yaon.ejb.entity.Account;
import fr.yaon.ejb.entity.Transaction;
import fr.yaon.ejb.persist.LocalPersist;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/banque/unified")
public class UnifiedServlet extends HttpServlet {
    @EJB
    LocalPersist persist;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        Collection<Account> accounts = persist.listAllAccounts();
        StringBuilder resultString = new StringBuilder();
        resultString.append("<html>");
        resultString.append("<head>");
        resultString.append("<link href=\"/banque-web/ow2_jonas.css\" rel=\"stylesheet\"/>");
        resultString.append("</head>");

        resultString.append("<body>");
        resultString.append("<table>");
        for(Account account : accounts) {
            Collection<Transaction> transactions = persist.findTransactions(account);
            resultString
                    .append("<tr>")
                    .append("<td rowspan=\"")
                    .append((!transactions.isEmpty() ? transactions.size() : 1))
                    .append("\">")
                    .append(account.getId())
                    .append("</td>");

            if(transactions.isEmpty()) {
                resultString
                        .append("<td>")
                        .append("</td>")
                        .append("<td>")
                        .append("</td>")
                        .append("</tr>");
            }else {
                Transaction[] transactionsArray = new Transaction[transactions.size()];
                transactions.toArray(transactionsArray);

                for (int i = 0; i < transactionsArray.length; i++) {
                    resultString
                            .append("<td>")
                            .append(transactionsArray[i].getSource() == null ? "Receveur" : transactionsArray[i].getSource().getId() == account.getId() ? "Emetteur" : "Receveur")
                            .append("</td>")
                            .append("<td>")
                            .append(transactionsArray[i].getBalance())
                            .append("</td>")
                            .append("</tr>");
                }
            }
        }
        resultString.append("</table>");
        resultString.append("</body>");
        resultString.append("</html>");


        response.getWriter().println(resultString);
    }
}
