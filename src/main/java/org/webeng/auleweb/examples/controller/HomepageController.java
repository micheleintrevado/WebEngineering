/*
 * HomepageController.java
 *
 *
 */
package org.webeng.auleweb.examples.controller;

import org.webeng.auleweb.examples.application.ApplicationBaseController;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class HomepageController extends ApplicationBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Homepage");
        result.activate("homepage.ftl.html", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        action_default(request, response);

    }

}
