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
            modifica_evento(request, response);
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

    private void modifica_evento(HttpServletRequest request, HttpServletResponse response) {
        try {
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

            // FARE UPDATE INVECE DI INSERT, QUINDI BISOGNA TOGLIERE NEW IMPL()
            dataLayer.getEventoDAO().storeEvento(
                    new EventoImpl(request.getParameter("nome"),
                            Date.valueOf(request.getParameter("giorno")),
                            Time.valueOf(request.getParameter("orario_inizio") + ":00"),
                            Time.valueOf(request.getParameter("orario_fine") + ":00"),
                            request.getParameter("descrizione"),
                            TipoEvento.valueOf(request.getParameter("tipologia")),
                            responsabile,
                            ricorrenza,
                            aula,
                            corso)
            );
            response.sendRedirect(Objects.requireNonNullElse(request.getParameter(REFERRER), "eventi"));
            //TemplateResult result = new TemplateResult(getServletContext());
            //AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            //List<Evento> eventi = dataLayer.getEventoDAO().getEventi();
            //result.activate("eventi/add.ftl", request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            //handleError(ex, request, response);
        }
    }

    private void action_logged(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException {
        TemplateResult result = new TemplateResult(getServletContext());
        Evento eventoDaModificare = ((AulewebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEvento(Integer.valueOf(request.getParameter("id_evento")));
        //List<Evento> eventi = ((AulewebDataLayer) request.getAttribute("datalayer")).getEventoDAO().getEventi();
        List<TipoEvento> tipologieEvento = new ArrayList<>(Arrays.asList(TipoEvento.values()));
        List<Responsabile> responsabili = ((AulewebDataLayer) request.getAttribute("datalayer")).getResponsabileDAO().getResponsabili();
        List<Aula> aule = ((AulewebDataLayer) request.getAttribute("datalayer")).getAulaDAO().getAule();
        List<Corso> corsi = ((AulewebDataLayer) request.getAttribute("datalayer")).getCorsoDAO().getCorsi();
        List<TipoRicorrenza> tipiRicorrenza = new ArrayList<>(Arrays.asList(TipoRicorrenza.values()));

        // request.setAttribute("eventi", Objects.requireNonNullElse(eventi, ""));
        request.setAttribute("evento", eventoDaModificare);
        request.setAttribute("tipologiaEvento", tipologieEvento);
        request.setAttribute("Responsabili", responsabili);
        request.setAttribute("Aule", aule);
        request.setAttribute("Corsi", corsi);
        request.setAttribute("TipiRicorrenza", tipiRicorrenza);
        result.activate("eventi/edit.ftl", request, response);
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
