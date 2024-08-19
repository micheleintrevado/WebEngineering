package org.webeng.auleweb.controller.gruppi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webeng.auleweb.application.AulewebBaseController;
import org.webeng.auleweb.application.AulewebDataLayer;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.framework.data.DataException;
import com.google.gson.Gson;
import org.webeng.auleweb.framework.view.TemplateManagerException;
import org.webeng.auleweb.framework.view.TemplateResult;

/**
 *
 * @author enric
 */
public class ShowGruppi extends AulewebBaseController {

    public static final String REFERRER = "referrer";

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");

        if ("getAuleCorsi".equals(action)) {
            action_getAuleCorsi(request, response);
        } else {
            action_default(request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        try {
            TemplateResult result = new TemplateResult(getServletContext());
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            List<Gruppo> gruppi = dataLayer.getGruppoDAO().getGruppi();
            for (Gruppo gruppo : gruppi) {
                List<Aula> aule = dataLayer.getAulaDAO().getAuleByGruppo(gruppo);
                gruppo.setAule(aule);
            }
            List<Corso> corsi = dataLayer.getCorsoDAO().getCorsi();

            request.setAttribute("gruppi", gruppi);
            request.setAttribute("corsi", corsi);
            result.activate("gruppi/get.ftl", request, response);
        } catch (DataException | TemplateManagerException ex) {
            handleError(ex, request, response);
        }
    }

    private void action_getAuleCorsi(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            AulewebDataLayer dataLayer = (AulewebDataLayer) request.getAttribute("datalayer");
            int idGruppo = Integer.valueOf(request.getParameter("id_gruppo"));

            Gruppo gruppo = dataLayer.getGruppoDAO().getGruppo(idGruppo);
            List<Aula> aule = dataLayer.getAulaDAO().getAuleByGruppo(gruppo);
            List<Corso> corsi = dataLayer.getCorsoDAO().getCorsiByGruppo(gruppo);

            // Crea una mappa di risposta
            Map<String, List<Map<String, String>>> responseData = new HashMap<>();

            // Prepara le aule
            List<Map<String, String>> auleData = aule.stream()
                    .map(aula -> {
                        Map<String, String> aulaMap = new HashMap<>();
                        aulaMap.put("id", String.valueOf(aula.getKey()));
                        aulaMap.put("nome", aula.getNome());
                        return aulaMap;
                    })
                    .toList();

            // Prepara i corsi
            List<Map<String, String>> corsiData = corsi.stream()
                    .map(corso -> {
                        Map<String, String> corsoMap = new HashMap<>();
                        corsoMap.put("id", String.valueOf(corso.getKey()));
                        corsoMap.put("nome", corso.getNome());
                        return corsoMap;
                    })
                    .toList();

            responseData.put("aule", auleData);
            responseData.put("corsi", corsiData);

            // Converte la mappa in JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseData);

            // Imposta il tipo di contenuto e invia la risposta
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write(jsonResponse);
            out.flush();
        } catch (DataException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet per visualizzare i gruppi e gestire il recupero dinamico di aule e corsi";
    }
}
