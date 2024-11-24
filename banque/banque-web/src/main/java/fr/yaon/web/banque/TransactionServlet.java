package fr.yaon.web.banque;

import fr.yaon.ejb.entity.Transaction;
import fr.yaon.ejb.persist.LocalPersist;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/banque/transactions")
public class TransactionServlet extends HttpServlet {
    @EJB
    LocalPersist persist;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        StringBuilder responseString = new StringBuilder();
        Collection<Transaction> transactions = persist.listAllTransactions();

        responseString.append("<table>");
        for (Transaction transaction : transactions) {
            responseString
                    .append("<tr>")
                    .append("<td>")
                    .append(transaction.getId())
                    .append("</td>")
                    .append("<td>")
                    .append(transaction.getSource())
                    .append("</td>")
                    .append("<td>")
                    .append(transaction.getDestination())
                    .append("</td>")
                    .append("<td>")
                    .append(transaction.getBalance())
                    .append("</td>")
                    .append("</tr>");
        }
        responseString.append("</table>");

        response.getWriter().println(responseString);
    }
}
