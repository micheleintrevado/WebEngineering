/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.responsabili;

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
import org.webeng.auleweb.data.model.Evento;
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
public class EditResponsabile extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
            var associazione = request.getParameter("associa");
            if (associazione != null) {
                modifica_responsabile(request, response, associazione);
                response.sendRedirect(request.getContextPath() + "/modifica-responsabile?id_responsabile=" + request.getParameter("id_responsabile"));
            } else {
                modifica_responsabile(request, response, null);
                response.sendRedirect(request.getContextPath() + "/modifica-responsabile?id_responsabile=" + request.getParameter("id_responsabile"));
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

    private void modifica_responsabile(HttpServletRequest request, HttpServletResponse response, String associazione) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Responsabile responsabileDaModificare = dataLayer.getResponsabileDAO().getResponsabile(Integer.valueOf(request.getParameter("id_responsabile")));
            if (associazione == null) {
                responsabileDaModificare.setNome(request.getParameter("nome"));
                responsabileDaModificare.setEmail(request.getParameter("email"));

                dataLayer.getResponsabileDAO().storeResponsabile(responsabileDaModificare);
            } else if (associazione.equals("associaEvento")){
                Evento eventoToAssign = dataLayer.getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
                dataLayer.getEventoDAO().assignResponsabile(eventoToAssign, responsabileDaModificare);
            } else if (associazione.equals("associaAula")) {
                Aula aulaToAssign = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
                aulaToAssign.setResponsabile(responsabileDaModificare);
                dataLayer.getAulaDAO().storeAula(aulaToAssign);
            }
        } catch (Exception ex) {
            handleError(request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadResponsabileData(request, response);
        result.activate("responsabili/edit.ftl", request, response);
    }

    private void loadResponsabileData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Responsabile responsabileDaModificare = dataLayer.getResponsabileDAO().getResponsabile(Integer.valueOf(request.getParameter("id_responsabile")));
        List<Aula> aule = dataLayer.getAulaDAO().getAule();
        List<Evento> eventi = dataLayer.getEventoDAO().getEventi();

        List<Aula> auleResponsabile = dataLayer.getAulaDAO().getAuleByResponsabile(responsabileDaModificare);
        List<Evento> eventiResponsabile = dataLayer.getEventoDAO().getEventiByResponsabile(responsabileDaModificare);
        request.setAttribute("auleResponsabile", auleResponsabile);
        request.setAttribute("eventiResponsabile", eventiResponsabile);

        request.setAttribute("responsabile", responsabileDaModificare);
        request.setAttribute("Aule", aule);
        request.setAttribute("Eventi", eventi);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
