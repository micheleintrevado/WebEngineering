package org.webeng.auleweb.controller.responsabili;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author miche
 */
public class InfoResponsabile  extends AulewebBaseController{

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            
            int idRresponsabile = Integer.valueOf(request.getParameter("id_responsabile"));
            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabile(idRresponsabile);
            List<Aula> aule = dataLayer.getAulaDAO().getAuleByResponsabile(responsabile);
            List<Evento> eventi = dataLayer.getEventoDAO().getEventiByResponsabile(responsabile);
            
            request.setAttribute("responsabile", responsabile);
            request.setAttribute("aule", aule);
            request.setAttribute("eventi", eventi);
            result.activate("responsabili/info.ftl", request, response);
        }catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }
    
}
