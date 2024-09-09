package org.webeng.auleweb.controller.aule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.impl.AulaImpl;

/**
 *
 * @author miche
 */
public class UploadAuleCSV extends AulewebBaseController {
    
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
                    action_upload_aule_csv(request, response);
                    response.sendRedirect(request.getContextPath() + "/aule");
                }
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }
    
    private void action_upload_aule_csv(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, DataException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Part p = request.getPart("aule-csv");
        if (p != null) {
            String name = p.getSubmittedFileName();
            long size = p.getSize();
            if (size > 0 && name != null && !name.isBlank()) {
                try (InputStream inputStream = p.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    // Salta l'intestazione se presente
                    if ((line = reader.readLine()) != null && line.contains(",")) {
                        // L'intestazione è presente, quindi la salto
                    }
                    // Processa ogni riga del file CSV
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",(?=(?:[^\\[]*\\[[^\\]]*\\])*[^\\]]*$)");
                        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                        Matcher matcher = pattern.matcher(line);
                        String nome = fields[0].trim();
                        String luogo = fields[1].trim();
                        String edificio = fields[2].trim();
                        String piano = fields[3].trim();
                        int capienza = Integer.valueOf(fields[4].trim());
                        int preseElettriche = Integer.valueOf(fields[5].trim());
                        int preseRete = Integer.valueOf(fields[6].trim());
                        String note = fields[7].trim();
                        int idResponsabile = Integer.valueOf(fields[8].trim());
                        Responsabile resp = dataLayer.getResponsabileDAO().getResponsabile(idResponsabile);
                        
                        String attrezzatureKeys = "";                        
                        if (matcher.find()) {
                            attrezzatureKeys = matcher.group(1);
                        }
                        String[] attrezzatureKeysArray = attrezzatureKeys.split(",");                        
                        
                        String gruppiKeys = "";                        
                        if (matcher.find()) {
                            gruppiKeys = matcher.group(1);
                        }
                        String[] gruppiKeysArray = gruppiKeys.split(",");                        
                        
                        Aula aula = new AulaImpl(nome, luogo, edificio, piano, capienza, resp, preseElettriche, preseRete, note, null, null);
                        dataLayer.getAulaDAO().storeAula(aula);
                        
                        for (String key : attrezzatureKeysArray) {
                            if (!key.equals("")){
                                Attrezzatura attrezzatura = dataLayer.getAttrezzaturaDAO().getAttrezzatura(Integer.valueOf(key));
                                dataLayer.getAttrezzaturaDAO().assignAttrezzatura(attrezzatura, aula);
                            }
                        }
                        for (String key : gruppiKeysArray) {
                            if (!key.equals("")){
                                Gruppo gruppo = dataLayer.getGruppoDAO().getGruppo(Integer.valueOf(key));
                                dataLayer.getGruppoDAO().assignGruppo(gruppo, aula);
                            }
                        }
                    }
                } catch (DataException ex) {
                    handleError(ex, request, response);
                }
            }
        }
    }
    
    private void action_anonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String completeRequestURL = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        request.setAttribute("referrer", completeRequestURL);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
