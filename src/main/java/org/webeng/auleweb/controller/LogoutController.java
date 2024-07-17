/*
 * LogoutController.java
 *
 *
 */
package org.webeng.auleweb.controller;

import org.webeng.auleweb.framework.security.SecurityHelpers;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class LogoutController extends AulewebBaseController {

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
