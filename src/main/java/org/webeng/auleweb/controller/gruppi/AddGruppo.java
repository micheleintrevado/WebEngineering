package org.webeng.auleweb.controller.gruppi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.impl.GruppoImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class AddGruppo extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
            aggiungi_gruppo(request, response);
        } else {
            try {
                HttpSession s = SecurityHelpers.checkSession(request);
                String https_redirect_url = SecurityHelpers.checkHttps(request);
                request.setAttribute("https-redirect", https_redirect_url);
                if (s == null) {
                    action_anonymous(request, response);
                } else {
                    int admin_id = (int) s.getAttribute("userid");
                    AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
                    AdminDAO adminDAO = dataLayer.getAdminDAO();
                    //ottengo l'utente tramite la sua id nella sessione
                    Admin admin = adminDAO.getAdmin(admin_id);
                    if (admin != null) {
                        request.setAttribute("admin", admin);
                        action_logged(request, response);
                    }
                }
            } catch (DataException | IOException ex) {
                handleError(ex, request, response);
            }
        }
    }

    private void aggiungi_gruppo(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));

            List<Aula> aule = new ArrayList<>();
            for (var aula : request.getParameterValues("aule")) {
                aule.add(dataLayer.getAulaDAO().getAula(Integer.valueOf(aula)));
            }

            Gruppo g = new GruppoImpl(
                    request.getParameter("nome"),
                    request.getParameter("descrizione"),
                    aule);

            dataLayer.getGruppoDAO().storeGruppo(g);
            for (Aula aula : aule) {
                dataLayer.getGruppoDAO().assignGruppo(g, aula);
            }

            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "gruppi"));
        } catch (Exception ex) {
            ex.printStackTrace();
            //handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        List<Aula> aule = dataLayer.getAulaDAO().getAule();

        request.setAttribute("Aule", aule);

        result.activate("gruppi/add.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
