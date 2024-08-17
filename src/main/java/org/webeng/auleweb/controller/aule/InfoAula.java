package org.webeng.auleweb.controller.aule;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class InfoAula extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        action_default(request, response);
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            int id = Integer.valueOf(request.getParameter("id_aula"));
            Aula aula = dataLayer.getAulaDAO().getAula(id);
            List<Evento> eventi = dataLayer.getEventoDAO().getEventiByAula(aula);
            aula.setGruppi(dataLayer.getGruppoDAO().getGruppiByAula(aula));
            aula.setAttrezzature(dataLayer.getAttrezzaturaDAO().getAttrezzaturaByAula(aula));
            request.setAttribute("aula", aula);
            request.setAttribute("eventi", eventi);
            result.activate("aule/info.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare un'aula";
    }
}
