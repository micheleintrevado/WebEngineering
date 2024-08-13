/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.attrezzature;

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
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class EditAttrezzatura extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
            if (request.getParameter("remove") != null) {
                elimina_attrezzatura(request, response);
                response.sendRedirect(request.getContextPath() + "/attrezzature");
            } else {
                modifica_attrezzatura(request, response);
                response.sendRedirect(request.getContextPath() + "/modifica-attrezzatura?id_attrezzatura=" + request.getParameter("id_attrezzatura"));
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

    private void elimina_attrezzatura(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Attrezzatura attrezzaturaDaModificare = dataLayer.getAttrezzaturaDAO().getAttrezzatura(Integer.valueOf(request.getParameter("id_attrezzatura")));
            dataLayer.getAttrezzaturaDAO().deleteAttrezzatura(attrezzaturaDaModificare);
            
        } catch (Exception ex) {
            handleError(request, response);
        }

    }

    private void modifica_attrezzatura(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Attrezzatura attrezzaturaDaModificare = dataLayer.getAttrezzaturaDAO().getAttrezzatura(Integer.valueOf(request.getParameter("id_attrezzatura")));
            //if (associazione == null) {
            if (request.getParameter("tipo") != null) {
                attrezzaturaDaModificare.setTipo(request.getParameter("tipo"));
                dataLayer.getAttrezzaturaDAO().storeAttrezzatura(attrezzaturaDaModificare);
            } else if (request.getParameter("associa") != null && request.getParameter("associa").equals("associaAula")) {
                Aula aulaToAssign = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
                dataLayer.getAttrezzaturaDAO().assignAttrezzatura(attrezzaturaDaModificare, aulaToAssign);
            } else if (request.getParameter("disassocia") != null && request.getParameter("disassocia").equals("disassociaAula")) {
                Aula aulaToAssign = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
                dataLayer.getAttrezzaturaDAO().deleteAttrezzaturaByAula(aulaToAssign, attrezzaturaDaModificare);
            }
        } catch (Exception ex) {
            handleError(request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadAttrezzatureData(request, response);
        result.activate("attrezzature/edit.ftl", request, response);
    }

    private void loadAttrezzatureData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Attrezzatura attrezzaturaDaModificare = dataLayer.getAttrezzaturaDAO().getAttrezzatura(Integer.valueOf(request.getParameter("id_attrezzatura")));
        List<Aula> aule = dataLayer.getAulaDAO().getAule();

        List<Aula> auleAttrezzatura = dataLayer.getAulaDAO().getAuleByAttrezzatura(attrezzaturaDaModificare);
        request.setAttribute("auleAttrezzatura", auleAttrezzatura);
        request.setAttribute("attrezzatura", attrezzaturaDaModificare);
        request.setAttribute("Aule", aule);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
