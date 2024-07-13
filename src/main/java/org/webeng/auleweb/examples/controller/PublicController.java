/*
 * PublicController.java
 *
 *
 */
package org.webeng.auleweb.examples.controller;

import org.webeng.auleweb.data.model.Article;
import org.webeng.auleweb.examples.application.ApplicationDataLayer;
import org.webeng.auleweb.examples.application.ApplicationBaseController;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;
import java.io.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class PublicController extends ApplicationBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {
        ApplicationDataLayer dl = (ApplicationDataLayer) request.getAttribute("datalayer");
        List<Article> articles = dl.getArticleDAO().getArticles();
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Articles");
        request.setAttribute("articles", articles);
        result.activate("public.ftl.html", request, response);

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }
}
