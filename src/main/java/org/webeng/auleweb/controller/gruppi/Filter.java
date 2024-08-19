package org.webeng.auleweb.controller.gruppi;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author miche
 */
public class Filter extends AulewebBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TemplateResult result = new TemplateResult(getServletContext());
        loadData(request, response);
        result.activate("gruppi/filtro.ftl", request, response);
    }

    private void loadData(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Gruppo gruppo = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(request.getParameter("id_gruppo")));
            Date inizioSettimana = Date.valueOf(request.getParameter("inizio_settimana"));
            // List<Evento> eventiAttuali = dataLayer.getEventoDAO().getEventiAttualiByGruppo(gruppo);
            // List<Evento> eventiProssimi = dataLayer.getEventoDAO().getEventiProssimiByGruppo(3, gruppo);
            request.setAttribute("inizioSettimana", inizioSettimana);
            request.setAttribute("gruppo", gruppo);
            List<Aula> auleGruppo = dataLayer.getAulaDAO().getAuleByGruppo(gruppo);

            if (request.getParameter("eventi_attuali") != null) {
                List<Evento> eventiAttuali = dataLayer.getEventoDAO().getEventiAttuali();
                HashMap<Aula, Evento> auleEventiAttuali = new HashMap();
                for (Aula aula : auleGruppo) {
                    for (Evento eventoAttuale : eventiAttuali) {
                        if (aula.getKey() == eventoAttuale.getAula().getKey()) {
                            auleEventiAttuali.put(aula, eventoAttuale);
                        }
                    }
                }
                request.setAttribute("auleEventiAttuali", auleEventiAttuali);
            }
            if (request.getParameter("eventi_prossimi") != null) {
                List<Evento> eventiProssimi = dataLayer.getEventoDAO().getEventiProssimi(3);
                HashMap<Aula, List<Evento>> auleEventiProssimi = new HashMap();
                for (Aula aula : auleGruppo) {
                    List<Evento> eventiAulaProssimi = new ArrayList();
                    for (Evento eventoProssimo : eventiProssimi) {
                        if (aula.getKey() == eventoProssimo.getAula().getKey()) {
                            eventiAulaProssimi.add(eventoProssimo);
                        }
                    }
                    auleEventiProssimi.put(aula, eventiAulaProssimi);
                }
                request.setAttribute("auleEventiProssimi", auleEventiProssimi);
            }
            if (request.getParameter("id_aula") != null && inizioSettimana != null) {
                // Lista di eventi associati a una specifica aula in una determinata settimana
                int id_aula = Integer.valueOf(request.getParameter("id_aula"));
                Aula aulaSettimana = dataLayer.getAulaDAO().getAula(id_aula);
                List<Evento> eventiAulaSettimana = dataLayer.getEventoDAO().getEventiSettimana(aulaSettimana, inizioSettimana);
                request.setAttribute("aulaSettimana", aulaSettimana);
                request.setAttribute("eventiAulaSettimana", eventiAulaSettimana);
            }
            if (inizioSettimana != null) {
                // Lista di eventi per ciascuna aula del gruppo scelto nella settimana indicata
                HashMap<Aula, List<Evento>> auleEventi = new HashMap();
                for (Aula aula : auleGruppo) {
                    List<Evento> eventiAuleSettimana = dataLayer.getEventoDAO().getEventiSettimana(aula, inizioSettimana);
                    auleEventi.put(aula, eventiAuleSettimana);
                }
                request.setAttribute("auleEventiSettimana", auleEventi);
            }
            if (request.getParameter("aule_giorno") != null) {
                HashMap<Aula, List<Evento>> auleEventiGiorno = new HashMap();
                for (Aula aula : auleGruppo) {
                    List<Evento> eventiAuleGiorno = dataLayer.getEventoDAO().getEventiAulaGiorno(aula, inizioSettimana);
                    auleEventiGiorno.put(aula, eventiAuleGiorno);
                }
                request.setAttribute("auleEventiGiorno", auleEventiGiorno);
            }
            if (request.getParameter("id_corso") != null && inizioSettimana != null) {
                // Lista di eventi per uno specifico corso in una determinata settimana
                int id_corso = Integer.valueOf(request.getParameter("id_corso"));
                Corso corsoSettimana = dataLayer.getCorsoDAO().getCorso(id_corso);
                List<Evento> eventiCorsoSettimana = dataLayer.getEventoDAO().getEventiSettimana(corsoSettimana, inizioSettimana);
                request.setAttribute("corsoSettimana", corsoSettimana);
                request.setAttribute("eventiCorsoSettimana", eventiCorsoSettimana);
            }

        } catch (DataException ex) {
            handleError(request, response);
        }

    }

}
