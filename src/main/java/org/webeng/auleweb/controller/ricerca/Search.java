package org.webeng.auleweb.controller.ricerca;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class Search extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        search_elements(request, response);
    }

    private void search_elements(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            request.setAttribute(REFERRER, request.getParameter(REFERRER));
            String keyword = request.getParameter("keyword");
            if (keyword != null && !keyword.equals("")) {
                List<Responsabile> responsabili = dataLayer.getResponsabileDAO().getResponsabiliBySearch(keyword);
                List<Aula> aule = dataLayer.getAulaDAO().getAuleBySearch(keyword);
                List<Evento> eventi = dataLayer.getEventoDAO().getEventiBySearch(keyword);
                List<Corso> corsi = dataLayer.getCorsoDAO().getCorsiBySearch(keyword);
                List<Gruppo> gruppi = dataLayer.getGruppoDAO().getGruppiBySearch(keyword);
                List<Attrezzatura> attrezzature = dataLayer.getAttrezzaturaDAO().getAttrezzatureBySearch(keyword);

                request.setAttribute("keyword", request.getParameter("keyword"));
                request.setAttribute("responsabili", responsabili);
                request.setAttribute("aule", aule);
                request.setAttribute("eventi", eventi);
                request.setAttribute("corsi", corsi);
                request.setAttribute("gruppi", gruppi);
                request.setAttribute("attrezzature", attrezzature);
                result.activate("ricerca/search.ftl", request, response);
            } else { 
                response.sendRedirect("homepage");
            }
        } catch (DataException | TemplateManagerException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    public String getServletInfo() {
        return "Servlet per la gestione dei risultati di ricerca";
    }
}
