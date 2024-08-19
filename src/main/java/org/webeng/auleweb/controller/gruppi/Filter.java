package org.webeng.auleweb.controller.gruppi;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author miche
 */
public class Filter extends AulewebBaseController{

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TemplateResult result = new TemplateResult(getServletContext());
        loadData(request, response);
        result.activate("gruppi/filtro.ftl", request, response);
    }
    
    private void loadData(HttpServletRequest request, HttpServletResponse response){        
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Gruppo gruppo = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(request.getParameter("id_gruppo")));
            Date inizioSettimana = Date.valueOf(request.getParameter("inizio_settimana"));
            List<Aula> aule = dataLayer.getAulaDAO().getAuleByGruppo(gruppo);
            HashMap<Aula, List <Evento>> auleEventi =  new HashMap();
            for (Aula aula : aule){
                List<Evento> eventiAulaSettimana = dataLayer.getEventoDAO().getEventiSettimana(aula, inizioSettimana);
                auleEventi.put(aula, eventiAulaSettimana);
            }
            
            request.setAttribute("aule_eventi_settimana", auleEventi);
        } catch (DataException ex) {
            handleError(request, response);
        }
        
    }
    
}
