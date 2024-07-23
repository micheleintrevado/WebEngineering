/*
 * LoginController.java
 *
 *
 */
package org.webeng.auleweb.controller;

import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Admin;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class LoginController extends AulewebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("referrer", request.getParameter("referrer"));
        result.activate("login.ftl.html", request, response);
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {
        String username = request.getParameter("u");
        String password = request.getParameter("p");

        AulewebDataLayer dl = (AulewebDataLayer) request.getAttribute("datalayer");
        if (!username.isEmpty() && !password.isEmpty()) {
            Admin a = dl.getAdminDAO().getAdminByName(username);
            try {
                String passwordHash = a.getPassword();//SecurityHelpers.getPasswordHashPBKDF2(a.getPassword());
                if (a != null && SecurityHelpers.checkPasswordHashPBKDF2(password, passwordHash)){
                    //se la validazione ha successo
                    //if the identity validation succeeds
                    SecurityHelpers.createSession(request, username, a.getKey());
                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    //if an origin URL has been transmitted, return to it
                    if (request.getParameter("referrer") != null) {
                        response.sendRedirect(request.getParameter("referrer"));
                    } else {
                        response.sendRedirect("homepage");
                    }
                    return;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        handleError("Login failed", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (request.getParameter("login") != null) {
            action_login(request, response);
        } else {
            String https_redirect_url = SecurityHelpers.checkHttps(request);
            request.setAttribute("https-redirect", https_redirect_url);
            action_default(request, response);
        }

    }

}
