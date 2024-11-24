package fr.yaon.web.banque;

import fr.yaon.ejb.entity.Account;
import fr.yaon.ejb.init.Initializer;
import fr.yaon.ejb.persist.LocalPersist;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/banque/accounts")
public class AccountServlet extends HttpServlet {
    @EJB
    LocalPersist persist;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        StringBuilder responseString = new StringBuilder();
        Collection<Account> accounts = persist.listAllAccounts();

        responseString.append("<table>");
        for (Account account : accounts) {
            responseString
                    .append("<tr>")
                    .append("<td>")
                    .append(account.getId())
                    .append("</td>")
                    .append("</tr>");
        }
        responseString.append("</table>");

        response.getWriter().println(responseString);
    }
}
