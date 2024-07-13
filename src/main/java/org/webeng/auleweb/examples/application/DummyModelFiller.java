package org.webeng.auleweb.examples.application;


import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.view.DataModelFiller;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author giuse
 */
public class DummyModelFiller implements DataModelFiller {

    @Override
    public void fillDataModel(Map datamodel, HttpServletRequest request, ServletContext context) {        
        try {
            datamodel.put("numberOfArticles", ((ApplicationDataLayer) request.getAttribute("datalayer")).getArticleDAO().getArticles().size());
        } catch (DataException ex) {
            Logger.getLogger(DummyModelFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
