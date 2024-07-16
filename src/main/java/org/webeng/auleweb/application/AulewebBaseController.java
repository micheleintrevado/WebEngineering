/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.application;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import org.webeng.auleweb.framework.controller.AbstractBaseController;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author enric
 */
public abstract class AulewebBaseController extends AbstractBaseController {

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new AulewebDataLayer(ds);
        } catch(SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
