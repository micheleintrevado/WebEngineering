/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.aule;

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
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.impl.AulaImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author miche
 */
public class AddAula extends AulewebBaseController{
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: CHECK LOGIN DI ADMIN/RESPONSABILE
        if (request.getMethod().equals("POST")) {
            aggiungi_aula(request, response);
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

    private void aggiungi_aula(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabile(Integer.valueOf(request.getParameter("id_responsabile")));
            
            List <Attrezzatura> attrezzature = new ArrayList<>();
            for( var att :  request.getParameterValues("attrezzatura")){
                attrezzature.add(dataLayer.getAttrezzaturaDAO().getAttrezzatura(Integer.valueOf(att)));
            }
            
            List <Gruppo> gruppi = new ArrayList<>();
            for( var gruppo :  request.getParameterValues("gruppi")){
                gruppi.add(dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(gruppo)));
                
            }
            
            dataLayer.getAulaDAO().storeAula(
                    new AulaImpl(
                            request.getParameter("nome"),
                            request.getParameter("luogo"),
                            request.getParameter("edificio"),
                            request.getParameter("piano"),
                            Integer.valueOf(request.getParameter("capienza")),
                            responsabile,
                            Integer.valueOf(request.getParameter("prese_elettriche")),
                            Integer.valueOf(request.getParameter("prese_rete")),
                            request.getParameter("note"),
                            null,
                            null
                            )
            );
            
            
            
            // TODO: PRENDERE ID AULA APPENA CREATA E GEGSTIRE INSERIMENTO ATTREZZATURA CON DAO
            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "aule"));
        }            
        catch (Exception ex) {
            ex.printStackTrace();
            //handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        List<Responsabile> responsabili = ((AulewebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getResponsabili();
        List<Attrezzatura> attrezzature = ((AulewebDataLayer) request.getAttribute("datalayer")).getAttrezzaturaDAO().getAttrezzatura();
        List<Gruppo> gruppi = ((AulewebDataLayer) request.getAttribute("datalayer")).getGruppoDAO().getGruppi();

        request.setAttribute("Responsabili", responsabili);
        request.setAttribute("Attrezzature", attrezzature);
        request.setAttribute("Gruppi", gruppi);
        
        result.activate("aule/add.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare di un evento";
    }
    
}
