/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class PublicController extends AulewebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {
        AulewebDataLayer dl = (AulewebDataLayer) request.getAttribute("datalayer");
        List<Aula> aule = dl.getAulaDAO().getAule();
        TemplateResult result = new TemplateResult(getServletContext());
        request.setAttribute("page_title", "Aule");
        request.setAttribute("aule", aule);
        result.activate("public.ftl.html", request, response);

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }
}
