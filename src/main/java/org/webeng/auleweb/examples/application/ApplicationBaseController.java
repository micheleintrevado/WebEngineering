package org.webeng.auleweb.examples.application;

import org.webeng.auleweb.framework.controller.AbstractBaseController;
import org.webeng.auleweb.framework.data.DataLayer;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 *
 * @author Giuseppe Della Penna
 */
public abstract class ApplicationBaseController extends AbstractBaseController {

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new ApplicationDataLayer(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}
