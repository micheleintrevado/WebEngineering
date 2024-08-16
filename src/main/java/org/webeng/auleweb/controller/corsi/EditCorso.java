package org.webeng.auleweb.controller.corsi;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class EditCorso extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
                modifica_corso(request, response);
                response.sendRedirect(request.getContextPath() + "/modifica-corso?id_corso=" + request.getParameter("id_corso"));
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

    private void modifica_corso(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Corso corsoDaModificare = dataLayer.getCorsoDAO().getCorso(Integer.valueOf(request.getParameter("id_corso")));
            if (request.getParameter("nome") != null) {
                corsoDaModificare.setNome(request.getParameter("nome"));
                dataLayer.getCorsoDAO().storeCorso(corsoDaModificare);
            } else if (request.getParameter("associa") != null && request.getParameter("associa").equals("associaEvento")) {
                Evento eventoToAssign = dataLayer.getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
                eventoToAssign.setCorso(corsoDaModificare);
                dataLayer.getEventoDAO().storeEvento(eventoToAssign);
            }
        } catch (Exception ex) {
            handleError(request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadCorsoData(request, response);
        result.activate("corsi/edit.ftl", request, response);
    }

    private void loadCorsoData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Corso corsoDaModificare = dataLayer.getCorsoDAO().getCorso(Integer.valueOf(request.getParameter("id_corso")));
        List<Evento> eventi = dataLayer.getEventoDAO().getEventi();

        List<Evento> eventiCorso = dataLayer.getEventoDAO().getEventiByCorso(corsoDaModificare);
        request.setAttribute("eventiCorso", eventiCorso);
        request.setAttribute("corso", corsoDaModificare);
        request.setAttribute("Eventi", eventi);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
