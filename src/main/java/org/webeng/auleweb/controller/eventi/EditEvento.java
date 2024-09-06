package org.webeng.auleweb.controller.eventi;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.webeng.auleweb.data.model.impl.RicorrenzaImpl;
import org.webeng.auleweb.framework.data.DataException;
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
            if (request.getAttribute("eventiWarning") != null || request.getAttribute("conflitto") != null) {
                warning_eventi(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/modifica-evento?id_evento=" + request.getParameter("id_evento"));
                // request.getRequestDispatcher("/modifica-evento?id_evento=" + request.getParameter("id_evento")).forward(request, response);
                // String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
                // request.getRequestDispatcher(completeRequestURL).forward(request, response);
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
            Corso corso = null;
            if (!request.getParameter("id_corso").equals("")){
                corso = dataLayer.getCorsoDAO().getCorso(Integer.valueOf(request.getParameter("id_corso")));
            }

            int id = Integer.valueOf(request.getParameter("id_evento"));
            // RECUPERO LE INFORMAZIONI INSERITE DALL'UTENTE
            String nome = request.getParameter("nome");
            Date giorno = Date.valueOf(request.getParameter("giorno"));
            Time orarioInizio = Time.valueOf(request.getParameter("orario_inizio") + ":00");
            Time orarioFine = Time.valueOf(request.getParameter("orario_fine") + ":00");
            String descrizione = request.getParameter("descrizione");
            TipoEvento tipoEvento = TipoEvento.valueOf(request.getParameter("tipologia"));
            Evento evento = dataLayer.getEventoDAO().getEvento(id);
            Ricorrenza ricorrenza;
            if (evento.getRicorrenza() != null) {
                ricorrenza = dataLayer.getRicorrenzaDAO().getRicorrenza(evento.getRicorrenza().getKey());
            } else {
                ricorrenza = null;
            }
            TipoRicorrenza tipoRicorrenza = null;
            Date fineRicorrenza = null;
            boolean isRicorrenzaModified = false;
            if (!request.getParameter("id_master").equals("")) {
                tipoRicorrenza = TipoRicorrenza.valueOf(request.getParameter("id_master"));
            }
            if (!request.getParameter("fine_ricorrenza").equals("")) {
                fineRicorrenza = Date.valueOf(request.getParameter("fine_ricorrenza"));
            }
            // SE L'UTENTE HA CANCELLATO LA RICORRENZA, ALLORA ELIMINO LA RICORRENZA ASSOCIATA ALL'EVENTO
            if (tipoRicorrenza == null || fineRicorrenza == null) {
                ricorrenza = null;
            }
            // SE L'EVENTO AVEVA UNA RICORRENZA E L'UTENTE NON L'HA CANCELLATA, EFFETTUO EVENTUALI MODIFICHE
            if (ricorrenza != null) {
                if (tipoRicorrenza != ricorrenza.getTipoRicorrenza() || !fineRicorrenza.equals(ricorrenza.getDataTermine())) {
                    isRicorrenzaModified = true;
                    // MODIFICO LA RICORRENZA GIA' ESISTENTE
                    ricorrenza.setTipoRicorrenza(tipoRicorrenza);
                    ricorrenza.setDataTermine(fineRicorrenza);
                    dataLayer.getRicorrenzaDAO().storeRicorrenza(ricorrenza);
                }
            } else if (ricorrenza == null && tipoRicorrenza != null && fineRicorrenza != null) {
                isRicorrenzaModified = true;
                // SE L'UTENTE HA INSERITO UNA RICORRENZA E L'EVENTO NON NE AVEVA UNA, CREO UNA NUOVA RICORRENZA
                Ricorrenza r = new RicorrenzaImpl(
                        tipoRicorrenza,
                        fineRicorrenza);
                dataLayer.getRicorrenzaDAO().storeRicorrenza(r);
                ricorrenza = dataLayer.getRicorrenzaDAO().getRicorrenza(r.getKey());
            }

            List<Evento> eventiRicorrenti = new ArrayList();
            List<Evento> eventiWarning = new ArrayList();
            Evento conflitto = null;

            // VERIFICA DI EVENTI IN CONFLITTO NEGLI STESSI AULA, GIORNO E ORARI
            conflitto = dataLayer.getEventoDAO().getEventiSovrapposti(evento, aula, giorno, orarioInizio, orarioFine);
            if (conflitto != null) {
                request.setAttribute("conflitto", conflitto);
            }
            // SE NON CI SONO CONFLITTI, EFFETTUO LE MODIFICHE APPORTATE
            if (conflitto == null) {
                evento.setNome(nome);
                evento.setGiorno(giorno);
                evento.setOrarioInizio(orarioInizio);
                evento.setOrarioFine(orarioFine);
                evento.setDescrizione(descrizione);
                evento.setTipoEvento(tipoEvento);
                evento.setResponsabile(responsabile);
                evento.setAula(aula);
                if (corso != null){
                    evento.setCorso(corso);
                } else {
                    evento.setCorso(null);
                }
                /*if (isRicorrenzaModified) {
                    if (editOthers) {
                        dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);
                    }
                }
                evento.setRicorrenza(ricorrenza);*/
                if (ricorrenza != null) { // !evento.getRicorrenza().getDataTermine().equals(ricorrenza.getDataTermine())
                    if (evento.getRicorrenza() != null && editOthers && isRicorrenzaModified) {
                        dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);
                    }
                    evento.setRicorrenza(ricorrenza);
                    if (isRicorrenzaModified) {
                        dataLayer.getEventoDAO().assignRicorrenza(evento, ricorrenza);
                    }
                    // MODIFICA E CREAZIONE DEGLI EVENTI RICORRENTI
                    if (editOthers && isRicorrenzaModified) {
                        eventiRicorrenti = dataLayer.getEventoDAO().createEventiRicorrenti(evento, ricorrenza);
                        dataLayer.getEventoDAO().updateEventiRicorrenti(evento, ricorrenza);
                        eventiWarning = dataLayer.getEventoDAO().getEventiNonInseriti(eventiRicorrenti);
                    } else if (editOthers) {
                        dataLayer.getEventoDAO().updateEventiRicorrenti(evento, ricorrenza);
                    }
                } else {
                    // ELIMINAZIONE DEGLI EVENTI RICORRENTI 
                    if (evento.getRicorrenza() != null && editOthers) {
                        dataLayer.getEventoDAO().deleteEventiRicorrenti(evento);
                    }
                    evento.setRicorrenza(ricorrenza);
                }
                dataLayer.getEventoDAO().storeEvento(evento);
            }

            // WARNING PER EVENTI NON INSERIBILI A CAUSA DI CONFLITTI
            if (!eventiWarning.isEmpty()) {
                request.setAttribute("eventiWarning", eventiWarning);
            }

            // response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "eventi"));
        } catch (Exception ex) {
            ex.printStackTrace();
            //handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadEventoData(request, response);
        result.activate("eventi/edit.ftl", request, response);
    }

    private void loadEventoData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Evento eventoDaModificare = dataLayer.getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
        List<TipoEvento> tipologieEvento = new ArrayList<>(Arrays.asList(TipoEvento.values()));
        List<Responsabile> responsabili = dataLayer.getResponsabileDAO().getResponsabili();
        List<Aula> aule = dataLayer.getAulaDAO().getAule();
        List<Corso> corsi = dataLayer.getCorsoDAO().getCorsi();
        List<TipoRicorrenza> tipiRicorrenza = new ArrayList<>(Arrays.asList(TipoRicorrenza.values()));

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

    private void warning_eventi(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadEventoData(request, response);
        result.activate("eventi/edit.ftl", request, response);
    }

}
