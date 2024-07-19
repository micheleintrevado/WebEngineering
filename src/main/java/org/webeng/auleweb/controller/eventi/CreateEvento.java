/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.eventi;

import freemarker.template.Template;
import java.awt.Window;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class CreateEvento extends AulewebBaseController {
    
    public static final String REFERRER = "referrer";
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: CHECK LOGIN DI ADMIN/RESPONSABILE
        action_default(request, response);
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            List<Evento> eventi = dataLayer.getEventoDAO().getEventi();
            request.setAttribute("eventi", eventi);
            result.activate("getEventi.ftl.html", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare di un evento"; 
    }
    
}
