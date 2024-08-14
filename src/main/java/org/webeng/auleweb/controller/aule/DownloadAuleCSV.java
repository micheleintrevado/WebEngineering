/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller.aule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.StreamResult;

/**
 *
 * @author miche
 */
public class DownloadAuleCSV extends AulewebBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                    action_download_aule_csv(request, response);
                    //response.sendRedirect(request.getContextPath() + "/modifica-responsabile?id_responsabile=" + request.getParameter("id_responsabile"));
                }
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_download_aule_csv(HttpServletRequest request, HttpServletResponse response) throws DataException {
        try {

            AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
            List<Aula> aule = dataLayer.getAulaDAO().getAule();

            StreamResult result = new StreamResult(getServletContext());
            File in = new File(getServletContext().getRealPath("") + File.separatorChar + "configurazione_aule.csv");
            FileWriter fileWriter = new FileWriter(in);
            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "NOME AULA", "LUOGO", "EDIFICIO", "PIANO", "CAPIENZA", "PRESE ELETTRICHE", "PRESE RETE", "NOTE", "ID_RESPONSABILE", "ATTREZZATURE", "GRUPPI")
                    .build();
            try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, format)) {
                for (Aula aula : aule) {
                    var id = aula.getKey();
                    var nomeAula = aula.getNome();
                    var luogo = aula.getLuogo();
                    var edificio = aula.getEdificio();
                    var piano = aula.getPiano();
                    var capienza = aula.getCapienza();
                    var preseElettriche = aula.getPreseElettriche();
                    var preseRete = aula.getPreseRete();
                    var note = aula.getNote();
                    var responsabile = aula.getResponsabile().getKey();

                    List<Attrezzatura> attrezzature = dataLayer.getAttrezzaturaDAO().getAttrezzaturaByAula(aula);
                    List<Gruppo> gruppi = dataLayer.getGruppoDAO().getGruppiByAula(aula);

                    String attrezzatureCSV = "";
                    if (!attrezzature.isEmpty()) {
                        attrezzatureCSV = "[";
                        for (var attr : attrezzature) {
                            attrezzatureCSV = attrezzatureCSV + attr.getKey().toString() + ",";
                        }
                        attrezzatureCSV = attrezzatureCSV.substring(0, attrezzatureCSV.length() - 1) + "]";
                    }

                    String gruppiCSV = "";
                    if (!gruppi.isEmpty()) {
                        gruppiCSV = "[";
                        for (var grp : gruppi) {
                            gruppiCSV = gruppiCSV + grp.getKey().toString() + ",";
                        }
                        gruppiCSV = gruppiCSV.substring(0, gruppiCSV.length() - 1) + "]";
                    }

                    csvPrinter.printRecord(id, nomeAula, luogo, edificio, piano, capienza, preseElettriche, preseRete, note, responsabile, attrezzatureCSV, gruppiCSV);
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

    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }

}
