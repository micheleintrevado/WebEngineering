/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.webeng.auleweb.data.dao.GruppoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.impl.proxy.GruppoProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class GruppoDAO_MySql extends DAO implements GruppoDAO{
    
    private PreparedStatement sGruppoByID;
    private PreparedStatement sGruppiByAula;
    private PreparedStatement sUnassignedGruppi;
    
    private PreparedStatement iGruppo;
    private PreparedStatement assignGruppoAula;
    
    private PreparedStatement dGruppo;
    

    public GruppoDAO_MySql(DataLayer d) {
        super(d);
    }
    
    public void init() throws DataException{
        try{
            super.init();
            sGruppoByID = connection.prepareStatement("select * from gruppo where id = ?");
            sGruppiByAula = connection.prepareStatement("");
            sUnassignedGruppi = connection.prepareStatement("");
   
            iGruppo = connection.prepareStatement("");
            assignGruppoAula = connection.prepareStatement("");
    
            dGruppo = connection.prepareStatement("");
            
        } catch (SQLException ex){
            throw new DataException("Error initializing attrezzatura data layer", ex);
        } 
    }

    @Override
    public Gruppo createGruppo() {
        return new GruppoProxy(getDataLayer());
    }
    
    private GruppoProxy createAttrezzatura(ResultSet rs) throws DataException{
        GruppoProxy g = (GruppoProxy) createGruppo();
        try {
            g.setKey(rs.getInt("id"));
            g.setNome(rs.getString("nome"));
            g.setDescrizione(rs.getString("descrizione"));
        } catch (SQLException e) {
            throw new DataException("Unable to create Attrezzatura object form ResultSet", e);
        }
        return g;
    }

    @Override
    public Gruppo getGruppo(int gruppo_key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Gruppo> getGruppiByAula(Aula aula) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Gruppo> getUnassignedGruppi() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void storeGruppo(Gruppo gruppo) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void assignGruppo(Gruppo gruppo, Aula aula) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteGruppo(Gruppo g) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
