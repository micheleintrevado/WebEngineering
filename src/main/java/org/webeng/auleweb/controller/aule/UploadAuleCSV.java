package org.webeng.auleweb.controller.aule;

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
                    //response.sendRedirect(request.getContextPath() + "/modifica-responsabile?id_responsabile=" + request.getParameter("id_responsabile"));
                }
            }
        } catch (DataException | IOException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_upload_aule_csv(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AulewebDataLayer dataLayer = ((AulewebDataLayer) request.getAttribute("datalayer"));
        Part p = request.getPart("aule-csv");
        if (p != null){
            String name = p.getSubmittedFileName();
            String contentType = p.getContentType();
            long size = p.getSize();
            if (size > 0 && name != null && !name.isBlank()) {
                name = SecurityHelpers.sanitizeFilename(name);
                Path target = Paths.get(getServletContext().getInitParameter("uploads.directory") + File.separatorChar + name);
                int guess = 0;
                while (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
                    target = Paths.get(getServletContext().getInitParameter("uploads.directory") + File.separatorChar + (++guess) + "_" + name);
                }
                try (InputStream temp_upload = p.getInputStream()) {
                    Files.copy(temp_upload, target, StandardCopyOption.REPLACE_EXISTING); //nio utility. Otherwise, use a buffer and copy from inputstream to fileoutputstream
                }
                byte[] buffer = new byte[10];
                int read = 0;
                try (InputStream is = new FileInputStream(target.toFile())) {
                    read = is.read(buffer);
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
