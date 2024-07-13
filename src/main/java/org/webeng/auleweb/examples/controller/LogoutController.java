/*
 * LogoutController.java
 *
 *
 */
package org.webeng.auleweb.examples.controller;

import org.webeng.auleweb.examples.application.ApplicationBaseController;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class LogoutController extends ApplicationBaseController {

    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityHelpers.disposeSession(request);
        if (request.getParameter("referrer") != null) {
            response.sendRedirect(request.getParameter("referrer"));
        } else {
            response.sendRedirect("homepage");
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_logout(request, response);
    }

}
