/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.eventi;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class AddEvento extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("POST")) {
            aggiungi_evento(request, response);
            if (request.getAttribute("eventiWarning") != null || request.getAttribute("eventoWarning") != null) {
                warning_eventi(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/aggiungi-evento");
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

    private void aggiungi_evento(HttpServletRequest request, HttpServletResponse response) {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            Responsabile responsabile = dataLayer.getResponsabileDAO().getResponsabile(Integer.valueOf(request.getParameter("id_responsabile")));
            Aula aula = dataLayer.getAulaDAO().getAula(Integer.valueOf(request.getParameter("id_aula")));
            Corso corso = null;
            if (!request.getParameter("id_corso").equals("")){
                corso = dataLayer.getCorsoDAO().getCorso(Integer.valueOf(request.getParameter("id_corso")));
            }
            Date giorno = Date.valueOf(request.getParameter("giorno"));
            Time orarioInizio = Time.valueOf(request.getParameter("orario_inizio") + ":00");
            Time orarioFine = Time.valueOf(request.getParameter("orario_fine") + ":00");
            List<Evento> eventiWarning = new ArrayList();

            if (!request.getParameter("fine_ricorrenza").equals("") && !request.getParameter("id_master").equals("")) {
                // aggiunta di un evento con ricorrenza
                var dataFineRicorrenza = Date.valueOf(request.getParameter("fine_ricorrenza"));
                var dataInizioEvento = Date.valueOf(request.getParameter("giorno"));

                Ricorrenza r = new RicorrenzaImpl(
                        TipoRicorrenza.valueOf(request.getParameter("id_master")),
                        dataFineRicorrenza);
                dataLayer.getRicorrenzaDAO().storeRicorrenza(r);
                // ESTRARRE L'ID DELLA RICORRENZA APPENA INSERITA
                Ricorrenza ricorrenza = dataLayer.getRicorrenzaDAO().getRicorrenza(r.getKey());

                // gestione delle singole istanze dell'evento da creare con ricorrenza
                //aggiungo a lista le date convertite in localDate, itero e infine aggiungo uno a uno
                List<LocalDate> dateIstanzeEvento = new ArrayList<>();
                // inizializzo l'indice dell'evento alla data di inizio dell'evento
                LocalDate cursor = dataInizioEvento.toLocalDate();

                while (cursor.isBefore(dataFineRicorrenza.toLocalDate())) {
                    switch (request.getParameter("id_master")) {
                        case "giornaliera" -> {
                            dateIstanzeEvento.add(cursor);
                            cursor = cursor.plusDays(1);
                        }
                        case "settimanale" -> {
                            dateIstanzeEvento.add(cursor);
                            cursor = cursor.plusWeeks(1);
                        }
                        case "mensile" -> {
                            dateIstanzeEvento.add(cursor);
                            cursor = cursor.plusMonths(1);
                        }
                        default -> {
                        }
                    }
                }

                for (LocalDate data : dateIstanzeEvento) {
                    Evento eventoRicorrente = new EventoImpl(request.getParameter("nome"),
                            Date.valueOf(data),
                            orarioInizio,
                            orarioFine,
                            request.getParameter("descrizione"),
                            TipoEvento.valueOf(request.getParameter("tipologia")),
                            responsabile,
                            ricorrenza,
                            aula,
                            corso);
                    if (dataLayer.getEventoDAO().getEventoByAulaGiornoOrario(aula, Date.valueOf(data), orarioInizio, orarioFine) != null) {
                        eventiWarning.add(eventoRicorrente);
                        request.setAttribute("eventiWarning", eventiWarning);
                    } else {
                        dataLayer.getEventoDAO().storeEvento(eventoRicorrente);
                    }
                }
            } else {
                // aggiunta di un evento non ricorrente
                if (dataLayer.getEventoDAO().getEventoByAulaGiornoOrario(aula, giorno, orarioInizio, orarioFine) != null) {
                    request.setAttribute("eventoWarning", aula);
                } else {
                    Evento evento = new EventoImpl(request.getParameter("nome"),
                            giorno,
                            orarioInizio,
                            orarioFine,
                            request.getParameter("descrizione"),
                            TipoEvento.valueOf(request.getParameter("tipologia")),
                            responsabile,
                            null,
                            aula,
                            corso);
                    dataLayer.getEventoDAO().storeEvento(evento);
                }
            }

            // response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "eventi"));
        } catch (Exception ex) {
            ex.printStackTrace();
            //handleError(ex, request, response);
        }
    }

    private void warning_eventi(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadEventoData(request, response);
        result.activate("eventi/add.ftl", request, response);
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        loadEventoData(request, response);
        result.activate("eventi/add.ftl", request, response);
    }

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
    
    private void loadEventoData(HttpServletRequest request, HttpServletResponse response) throws DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        List<TipoEvento> tipologieEvento = new ArrayList<>(Arrays.asList(TipoEvento.values()));
        List<Responsabile> responsabili = dataLayer.getResponsabileDAO().getResponsabili();
        List<Aula> aule = dataLayer.getAulaDAO().getAule();
        List<Corso> corsi = dataLayer.getCorsoDAO().getCorsi();
        List<TipoRicorrenza> tipiRicorrenza = new ArrayList<>(Arrays.asList(TipoRicorrenza.values()));

        request.setAttribute("tipologiaEvento", tipologieEvento);
        request.setAttribute("Responsabili", responsabili);
        request.setAttribute("Aule", aule);
        request.setAttribute("Corsi", corsi);
        request.setAttribute("TipiRicorrenza", tipiRicorrenza);
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare di un evento";
    }

}
