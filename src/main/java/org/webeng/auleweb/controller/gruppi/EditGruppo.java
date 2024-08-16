package org.webeng.auleweb.controller.gruppi;

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
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class EditGruppo extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
            if (request.getParameter("remove") != null) {
                elimina_gruppo(request, response);
                response.sendRedirect(request.getContextPath() + "/gruppi");
            } else {
                modifica_gruppo(request, response);
                response.sendRedirect(request.getContextPath() + "/modifica-gruppo?id_gruppo=" + request.getParameter("id_gruppo"));
            }
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

    private void elimina_gruppo(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Gruppo gruppoDaModificare = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(request.getParameter("id_gruppo")));
            dataLayer.getGruppoDAO().deleteGruppo(gruppoDaModificare);

        } catch (Exception ex) {
            handleError(request, response);
        }

    }

    private void modifica_gruppo(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Gruppo gruppoDaModificare = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(request.getParameter("id_gruppo")));
            if (request.getParameter("nome") != null && request.getParameter("descrizione") != null) {
                gruppoDaModificare.setNome(request.getParameter("nome"));
                gruppoDaModificare.setDescrizione(request.getParameter("descrizione"));
                dataLayer.getGruppoDAO().storeGruppo(gruppoDaModificare);
            } else if (request.getParameter("associa") != null && request.getParameter("associa").equals("associaAula")) {
                Aula aulaToAssign = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
                dataLayer.getGruppoDAO().assignGruppo(gruppoDaModificare, aulaToAssign);
            } else if (request.getParameter("disassocia") != null && request.getParameter("disassocia").equals("disassociaAula")) {
                Aula aulaToAssign = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
                dataLayer.getGruppoDAO().deleteGruppoByAula(aulaToAssign, gruppoDaModificare);
            }
        } catch (Exception ex) {
            handleError(request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadGruppoData(request, response);
        result.activate("gruppi/edit.ftl", request, response);
    }

    private void loadGruppoData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Gruppo gruppoDaModificare = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(request.getParameter("id_gruppo")));
        List<Aula> aule = dataLayer.getAulaDAO().getAule();

        List<Aula> auleGruppo = dataLayer.getAulaDAO().getAuleByGruppo(gruppoDaModificare);
        request.setAttribute("auleGruppo", auleGruppo);
        request.setAttribute("gruppo", gruppoDaModificare);
        request.setAttribute("Aule", aule);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
