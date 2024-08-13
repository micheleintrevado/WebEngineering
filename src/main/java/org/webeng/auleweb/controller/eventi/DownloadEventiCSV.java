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
            java.sql.Date rangeStart = null;
            java.sql.Date rangeEnd = null;
            
            System.out.println("FINE RANGE: " + request.getParameter("start-range"));            
            System.out.println("FINE RANGE: " + request.getParameter("end-range"));
            
            List<Evento> eventi = dataLayer.getEventoDAO().getEventiRange(null, null, null);
            
            StreamResult result = new StreamResult(getServletContext());
            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "eventi.csv");
            FileWriter fileWriter = new FileWriter(in);
            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "NOME AULA", "LUOGO", "EDIFICIO", "PIANO", "CAPIENZA", "PRESE ELETTRICHE", "PRESE RETE", "NOTE", "ID_RESPONSABILE", "ATTREZZATURE", "GRUPPI")
                    .build();
            try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, format)) {
                for (Evento evento : eventi) {
                    var id = evento.getKey();
//                    var nomeAula = aula.getNome();
//                    var luogo = aula.getLuogo();
//                    var edificio = aula.getEdificio();
//                    var piano = aula.getPiano();
//                    var capienza = aula.getCapienza();
//                    var preseElettriche = aula.getPreseElettriche();
//                    var preseRete = aula.getPreseRete();
//                    var note = aula.getNote();
//                    var responsabile = aula.getResponsabile().getKey();
//
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

                    csvPrinter.printRecord(id);
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
