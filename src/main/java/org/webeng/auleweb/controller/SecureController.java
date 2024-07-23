package org.webeng.auleweb.controller;

import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.security.SecurityHelpers;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author Ingegneria del Web
 * @version
 */
public class SecureController extends AulewebBaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {
        //preleviamo il data layer 
        //get the data layer
        AulewebDataLayer dl = (AulewebDataLayer) request.getAttribute("datalayer");

        //manipoliamo i dati usando le interfacce esposta dai DAO accessibili dal data layer
        //manipulate the data using the interfaces exposed by the DAOs accessible from the data layer
        List<Evento> eventi_totali = dl.getEventoDAO().getEventi();
        Evento old_latest_issue = eventi_totali.get(eventi_totali.size()-1);
        //        
        Evento new_issue = dl.getEventoDAO().createEvento();
        new_issue.setNome("A");
        new_issue.setDescrizione("Descrizione");
        dl.getEventoDAO().storeEvento(new_issue);
        //        
        Aula new_article = dl.getAulaDAO().createAula();
        Responsabile author = dl.getResponsabileDAO().getResponsabile(1); //assume it already exists
        if (author != null) {
            new_article.setResponsabile(author);
            new_article.setNome(SecurityHelpers.addSlashes("NEW AULA FOR EVENTO " + (old_latest_issue.getNome())));
            new_article.setNote(SecurityHelpers.addSlashes("aula text"));
            new_issue.setAula(new_article);
            dl.getAulaDAO().storeAula(new_article);
            //
            Evento new_latest_issue = eventi_totali.get(eventi_totali.size()-1);

            TemplateResult result = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Manipulation");
            request.setAttribute("oldLatestIssue", old_latest_issue);
            request.setAttribute("newIssue", new_issue);
            request.setAttribute("newArticle", new_article);
            request.setAttribute("newLatestIssue", new_latest_issue);

            result.activate("secure.ftl.html", request, response);
        } else {
            handleError("Cannot add article: undefined author", request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        action_default(request, response);
    }
}
