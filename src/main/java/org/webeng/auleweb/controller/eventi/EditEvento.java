package org.webeng.auleweb.controller.eventi;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoEvento;
import org.webeng.auleweb.data.model.TipoRicorrenza;
import org.webeng.auleweb.data.model.impl.EventoImpl;
import org.webeng.auleweb.data.model.impl.RicorrenzaImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class EditEvento extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (request.getMethod().equals("POST")) {
            String editAll = request.getParameter("modifica-tutti");
            if (editAll.equals("single")) {
                modifica_evento(request, response, false);
            } else if (editAll.equals("ricorrenti")) {
                modifica_evento(request, response, true); // WHERE id_master = ?
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

    // editOthers = true --> modifica tutti gli eventi con stessa ricorrenza di quello considerato
    // editOthers = false --> modifica solo l'evento considerato
    private void modifica_evento(HttpServletRequest request, HttpServletResponse response, boolean editOthers) {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabile(Integer.valueOf(request.getParameter("id_responsabile")));
            Aula aula = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
            Corso corso = dataLayer.getCorsoDAO().getCorso(Integer.valueOf(request.getParameter("id_corso")));
            Ricorrenza ricorrenza;
            if (!request.getParameter("id_master").equals("") && !request.getParameter("fine_ricorrenza").equals("")) {
                Ricorrenza r = new RicorrenzaImpl(
                        TipoRicorrenza.valueOf(request.getParameter("id_master")),
                        Date.valueOf(request.getParameter("fine_ricorrenza")));
                dataLayer.getRicorrenzaDAO().storeRicorrenza(r);
                ricorrenza = dataLayer.getRicorrenzaDAO().getRicorrenza(r.getKey());
            } else {
                ricorrenza = null;
            }
            int id = Integer.valueOf(request.getParameter("id_evento"));
            
            String nome = request.getParameter("nome");
            Date giorno = Date.valueOf(request.getParameter("giorno"));
            Time orarioInizio = Time.valueOf(request.getParameter("orario_inizio") + ":00");
            Time orarioFine = Time.valueOf(request.getParameter("orario_fine") + ":00");
            String descrizione = request.getParameter("descrizione");
            TipoEvento tipoEvento = TipoEvento.valueOf(request.getParameter("tipologia"));
            Evento evento = dataLayer.getEventoDAO().getEvento(id);
            evento.setNome(nome);
            evento.setGiorno(giorno);
            evento.setOrarioInizio(orarioInizio);
            evento.setOrarioFine(orarioFine);
            evento.setDescrizione(descrizione);
            evento.setTipoEvento(tipoEvento);
            evento.setResponsabile(responsabile);
            //if (ricorrenza != null) {
            //    System.out.println("Set ricorrenza: " + ricorrenza.getKey());
            //    evento.setRicorrenza(ricorrenza);
            //}
            evento.setAula(aula);
            evento.setCorso(corso);
            List<Evento> eventiRicorrenti = new ArrayList();
            List<Evento> eventiWarning = new ArrayList();
            // MODIFICA DEL SINGOLO EVENTO
            if (!editOthers) {
                /*if (ricorrenza == null) {
                    System.out.println("modifica singolo con ricorrenza null = " + editOthers);
                    
                    dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);
                }*/
                evento.setRicorrenza(ricorrenza);
                dataLayer.getEventoDAO().storeEvento(evento);
            } else // MODIFICA ALTRI EVENTI RICORRENTI A PARTIRE DA QUELLO CORRENTE
            if (editOthers) {
                if (ricorrenza != null) {
                    // AGGIORNO L'evento corrente ed i successivi con la ricorrenza impostata
                    dataLayer.getRicorrenzaDAO().storeRicorrenza(ricorrenza);
                    dataLayer.getEventoDAO().updateEventiRicorrenti(evento, ricorrenza);
                    eventiRicorrenti = dataLayer.getEventoDAO().createEventiRicorrenti(evento, ricorrenza);
                    eventiWarning = dataLayer.getEventoDAO().getEventiNonInseriti(eventiRicorrenti);
                    
                    //if (eventiWarning.size() > 0) {
                    //    request.setAttribute("eventiWarning", eventiWarning);
                    //}
                } else {
                    // AGGIORNO l'evento corrente aggiornando la ricorrenza ed elimino gli eventi successivi
                    if (evento.getRicorrenza() != null) {
                        dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);
                    }
                    evento.setRicorrenza(ricorrenza);
                    dataLayer.getEventoDAO().storeEvento(evento);
                    
                }
            }
            // ALTERNATIVA
            /*
            if (!eventiWarning.isEmpty()) {
                // Usa la sessione per passare l'attributo eventiWarning
                HttpSession session = request.getSession();
                session.setAttribute("eventiWarning", eventiWarning);
            }

            // Redirect alla pagina del form o a un'altra pagina appropriata
            response.sendRedirect("modifica-evento?id=" + evento.getId());
             */
            /*if (request.getAttribute("isRedirect") == null) {
                if (!eventiWarning.isEmpty()) {
                    request.setAttribute("eventiWarning", eventiWarning);
                }
                request.setAttribute("isRedirect", true); // Flag per evitare loop
                //request.getRequestDispatcher("/modifica-evento?id_evento=" + id).forward(request, response);
               
                
            }*/
            if (!eventiWarning.isEmpty()){
                request.setAttribute("eventiWarning", eventiWarning);
                loadEventoData(request, response);
                
                result.activate("eventi/edit.ftl", request, response);               
                String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
                request.getRequestDispatcher(completeRequestURL).forward(request, response);
                
                //return;
            }
            
            /*else {
                response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "eventi"));
            }*/

            // Inoltra alla pagina del form per mostrare l'alert
            //request.getRequestDispatcher("/modifica-evento?id_evento=" + id).forward(request, response);

            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "eventi"));
            //TemplateResult result = new TemplateResult(getServletContext());
            //AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            //List<Evento> eventi = dataLayer.getEventoDAO().getEventi();
            //result.activate("eventi/add.ftl", request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            // handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadEventoData(request, response);
        result.activate("eventi/edit.ftl", request, response);
    }
    
    private void loadEventoData(HttpServletRequest request, HttpServletResponse response) throws DataException{
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Evento eventoDaModificare = dataLayer.getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
        //List<Evento> eventi = ((AulewebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventi();
        List<TipoEvento> tipologieEvento = new ArrayList<>(Arrays.asList(TipoEvento.values()));
        List<Responsabile> responsabili = dataLayer.getResponsabileDAO().getResponsabili();
        List<Aula> aule = dataLayer.getAulaDAO().getAule();
        List<Corso> corsi = dataLayer.getCorsoDAO().getCorsi();
        List<TipoRicorrenza> tipiRicorrenza = new ArrayList<>(Arrays.asList(TipoRicorrenza.values()));

        // request.setAttribute("eventi", Objects.requireNonNullElse(eventi, ""));
        request.setAttribute("evento", eventoDaModificare);
        request.setAttribute("tipologiaEvento", tipologieEvento);
        request.setAttribute("Responsabili", responsabili);
        request.setAttribute("Aule", aule);
        request.setAttribute("Corsi", corsi);
        request.setAttribute("TipiRicorrenza", tipiRicorrenza);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet Modifica Evento";
    }

}
