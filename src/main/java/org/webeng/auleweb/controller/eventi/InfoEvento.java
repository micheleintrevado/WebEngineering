/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.eventi;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author miche
 */
public class InfoEvento extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        action_default(request, response);
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");

            Evento evento = dataLayer.getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
            Ricorrenza ricorrenza = dataLayer.getRicorrenzaDAO().getRicorrenzaByEvento(evento);
            if (ricorrenza != null) {
                evento.setRicorrenza(ricorrenza);
                List<Evento> eventiRicorrenti = dataLayer.getEventoDAO().getEventiRicorrenti(ricorrenza);
                eventiRicorrenti.remove(evento);
                request.setAttribute("eventiRicorrenti", eventiRicorrenti);
            }
            request.setAttribute("evento", evento);
            result.activate("eventi/info.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare di un evento";
    }
}
