package fr.yaon.web.banque;


import fr.yaon.ejb.init.Initializer;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    @EJB
    Initializer initializer;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        if (request.getParameter("scope") != null) {
            if (request.getParameter("scope").equals("delete")) {
                System.out.println("Deleting exemples");
                initializer.deleteEntities();
            }
        } else {
            System.out.println("Creating exemples");
            initializer.initializeEntities();
        }

        response.sendRedirect("/banque-web/index.jsf");
    }
}
