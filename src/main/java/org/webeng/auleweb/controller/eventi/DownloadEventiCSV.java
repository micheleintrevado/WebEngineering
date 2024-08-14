/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.eventi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.utils.ServletHelpers;
import org.webeng.auleweb.framework.view.StreamResult;

/**
 *
 * @author miche
 */
public class DownloadEventiCSV extends AulewebBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            action_download_eventi_csv(request, response);
        } catch (NumberFormatException ex) {
            ServletHelpers.handleError("The requested resource is unavailable", request, response, getServletContext());
        }
    }
    
    
    private void action_download_eventi_csv(HttpServletRequest request, HttpServletResponse response) throws DataException{
        try {
            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            
            java.sql.Date rangeStart = java.sql.Date.valueOf(request.getParameter("start-range"));
            java.sql.Date rangeEnd = java.sql.Date.valueOf(request.getParameter("end-range"));
            
            
            List<Evento> eventi = null;
            
            if (request.getParameter("corso") != null && request.getParameter("corso").equals("")){
                eventi = dataLayer.getEventoDAO().getEventiRange(rangeStart, rangeEnd);
            } else {
                int idCorso = Integer.valueOf(request.getParameter("corso"));
                Corso corso = dataLayer.getCorsoDAO().getCorso(idCorso);
                eventi = dataLayer.getEventoDAO().getEventiRangeCorso(rangeStart, rangeEnd, corso);
            }
            
            StreamResult result = new StreamResult(getServletContext());
            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "eventi.csv");
            FileWriter fileWriter = new FileWriter(in);
            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "NOME", "GIORNO", "ORARIO_INIZIO", "ORARIO_FINE", "DESCRIZIONE", "TIPOLOGIA",
                            "ID_RESPONSABILE", "ID_MASTER", "ID_AULA", "ID_CORSO")
                    .build();
            try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, format)) {
                for (Evento evento : eventi) {
                    var id = evento.getKey();
                    var nome = evento.getNome();
                    var giorno = evento.getGiorno();
                    var orario_inizio = evento.getOrarioInizio();
                    var orario_fine = evento.getOrarioFine();
                    var descrizione = evento.getDescrizione();
                    var tipologia = evento.getTipoEvento();
                    var responsabile = evento.getResponsabile().getKey();
                    var id_master = evento.getRicorrenza() != null ? evento.getRicorrenza().getKey() : "";
                    var aula = evento.getAula().getKey();
                    var corso = evento.getCorso() != null ? evento.getCorso().getKey() : "";
                    
//                    List<Attrezzatura> attrezzature = dataLayer.getAttrezzaturaDAO().getAttrezzaturaByAula(aula);
//                    List<Gruppo> gruppi = dataLayer.getGruppoDAO().getGruppiByAula(aula);
//
//                    String attrezzatureCSV = "";
//                    if (!attrezzature.isEmpty()) {
//                        attrezzatureCSV = "[";
//                        for (var attr : attrezzature) {
//                            attrezzatureCSV = attrezzatureCSV + attr.getKey().toString() + ",";
//                        }
//                        attrezzatureCSV = attrezzatureCSV.substring(0, attrezzatureCSV.length() - 1) + "]";
//                    }
//
//                    String gruppiCSV = "";
//                    if (!gruppi.isEmpty()) {
//                        gruppiCSV = "[";
//                        for (var grp : gruppi) {
//                            gruppiCSV = gruppiCSV + grp.getKey().toString() + ",";
//                        }
//                        gruppiCSV = gruppiCSV.substring(0, gruppiCSV.length() - 1) + "]";
//                    }
                    csvPrinter.printRecord(id, nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, responsabile, id_master, aula, corso);
                }
                csvPrinter.flush();
                csvPrinter.close();
                fileWriter.close();
            }
            result.setResource(in);
            result.activate(request, response);
        } catch (IOException ex) {
            handleError(request, response);
        }
    }
}
