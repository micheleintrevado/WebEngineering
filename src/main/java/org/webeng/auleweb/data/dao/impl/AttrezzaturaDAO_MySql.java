/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.webeng.auleweb.data.dao.AttrezzaturaDAO;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class AttrezzaturaDAO_MySql extends DAO implements AttrezzaturaDAO{

    private PreparedStatement sAttrezzaturaById;
    private PreparedStatement sAttrezzaturaAll;
    private PreparedStatement iAttrezzatura;
    private PreparedStatement iAssignAttrezzatura;
    
    public AttrezzaturaDAO_MySql(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try{
            super.init();
            sAttrezzaturaById = connection.prepareStatement("select * from attrezzatura where id = ?");
            sAttrezzaturaAll = connection.prepareStatement("select * from attrezzatura");
            iAssignAttrezzatura = connection.prepareStatement("insert into webeng.attrezzatura(`tipo`) values (?);", Statement.RETURN_GENERATED_KEYS);
            iAssignAttrezzatura = connection.prepareStatement("");
        } catch (SQLException ex){
            throw new DataException("Error initializing authors data layer", ex);
        }
        
    }

    @Override
    public Attrezzatura createAttrezzatura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Attrezzatura> getAttrezzatura() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaByAula(Aula aula) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void assignAttrezzatura(Attrezzatura attrezzatua, Aula aula) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
