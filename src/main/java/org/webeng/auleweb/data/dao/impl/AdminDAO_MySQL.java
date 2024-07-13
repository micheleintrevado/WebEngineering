/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import org.webeng.auleweb.ex.newspaper.data.model.impl.proxy.ArticleProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.model.Admin;

/**
 *
 * @author enric
 */
public class AdminDAO_MySQL extends DAO implements AdminDAO{

    private PreparedStatement sAdminByID;
    private PreparedStatement sAdmin, sAdminByIssue;
    private PreparedStatement iAdmin, uAdmin, dAdmin;

    public AdminDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public Admin createAdmin() {
        return new AdminProxy(getDataLayer());
    }
    
    public Admin createAdmin(ResultSet rs) {
        //TODO: CODE
        return null;
    }

    @Override
    public Admin getAdmin(int admin_key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Admin getAdminByName(String username) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void storeAdmin(Admin admin) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
