package org.webeng.auleweb.controller.corsi;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class ShowCorsi extends AulewebBaseController {
    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            List<Corso> corsi = dataLayer.getCorsoDAO().getCorsi();
            for (Corso corso : corsi) {
                List<Evento> eventi = dataLayer.getEventoDAO().getEventiByCorso(corso);
                corso.setEventi(eventi);
            }
            request.setAttribute("corsi", corsi);
            result.activate("corsi/get.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare i corsi";
    }
}