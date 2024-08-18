/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class HomepageController extends AulewebBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        try {
        TemplateResult result = new TemplateResult(getServletContext());
        AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
        List<Evento> eventiAttuali = dataLayer.getEventoDAO().getEventiAttuali();

        request.setAttribute("eventiAttuali", eventiAttuali);
        request.setAttribute("page_title", "Homepage");
        result.activate("homepage.ftl.html", request, response);
        } catch (DataException ex) {
            handleError(ex, request, response);
        }
    }

}
