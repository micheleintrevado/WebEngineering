package org.webeng.auleweb.controller.aule;

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
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class EditAula extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (request.getMethod().equals("POST")) {
            modifica_aula(request, response);
            response.sendRedirect(request.getContextPath() + "/modifica-aula?id_aula=" + request.getParameter("id_aula"));
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

    private void modifica_aula(HttpServletRequest request, HttpServletResponse response) {
        try {
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadAulaData(request, response);
        result.activate("aule/edit.ftl", request, response);
    }

    private void loadAulaData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Aula aulaDaModificare = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
        List<Responsabile> responsabili = dataLayer.getResponsabileDAO().getResponsabili();
        List<Attrezzatura> attrezzature = dataLayer.getAttrezzaturaDAO().getAttrezzatura();
        List<Gruppo> gruppi = dataLayer.getGruppoDAO().getGruppi();
        
        List<Attrezzatura> attrezzatureAula = dataLayer.getAttrezzaturaDAO().getAttrezzaturaByAula(aulaDaModificare);
        System.out.println(attrezzatureAula);
        List<Gruppo> gruppiAula = dataLayer.getGruppoDAO().getGruppiByAula(aulaDaModificare);
        request.setAttribute("attrezzatureAula", attrezzatureAula);
        request.setAttribute("gruppiAula", gruppiAula);

        request.setAttribute("aula", aulaDaModificare);
        request.setAttribute("Responsabili", responsabili);
        request.setAttribute("Attrezzature", attrezzature);
        request.setAttribute("Gruppi", gruppi);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet Modifica Aula";
    }

}
