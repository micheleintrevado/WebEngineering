package org.webeng.auleweb.controller.eventi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.fortuna.ical4j.data.CalendarOutputter;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.utils.ServletHelpers;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import net.fortuna.ical4j.validate.ValidationException;
import org.webeng.auleweb.framework.view.StreamResult;

/**
 *
 * @author miche
 */
public class DownloadEventiICAL extends AulewebBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            action_download_eventi_ical(request, response);
        } catch (NumberFormatException ex) {
            ServletHelpers.handleError("The requested resource is unavailable", request, response, getServletContext());
        }
    }

    private void action_download_eventi_ical(HttpServletRequest request, HttpServletResponse response) throws DataException, FileNotFoundException, IOException {
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));

            java.sql.Date rangeStart = java.sql.Date.valueOf(request.getParameter("start-range"));
            java.sql.Date rangeEnd = java.sql.Date.valueOf(request.getParameter("end-range"));

            List<Evento> eventi = null;

            if (request.getParameter("corso") != null && request.getParameter("corso").equals("")) {
                eventi = dataLayer.getEventoDAO().getEventiRange(rangeStart, rangeEnd);
            } else {
                int idCorso = Integer.valueOf(request.getParameter("corso"));
                Corso corso = dataLayer.getCorsoDAO().getCorso(idCorso);
                eventi = dataLayer.getEventoDAO().getEventiRangeCorso(rangeStart, rangeEnd, corso);
            }

            Calendar calendar = new Calendar();
            calendar.add(new ProdId("AuleWeb"));
            calendar.add(ImmutableVersion.VERSION_2_0);
            calendar.add(ImmutableCalScale.GREGORIAN);

            for (Evento e : eventi) {
                String summary = e.getNome() + ": " + e.getDescrizione();

                LocalDate data = e.getGiorno().toLocalDate();
                LocalTime orarioInizio = e.getOrarioInizio().toLocalTime();
                LocalTime orarioFine = e.getOrarioFine().toLocalTime();

                LocalDateTime dataOraInizio = LocalDateTime.of(data, orarioInizio);
                LocalDateTime dataOraFine = LocalDateTime.of(data, orarioFine);

                VEvent eventICal = new VEvent(dataOraInizio, dataOraFine, summary);
                calendar.add(eventICal);
            }
            StreamResult result = new StreamResult(getServletContext());
            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "eventi.ics");
            if (!in.getParentFile().exists()) {
                in.getParentFile().mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(in);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
            result.setResource(in);
            result.activate(request, response);
        } catch (IOException | ValidationException ex) {
            handleError(ex, request, response);
        }
    }
}
