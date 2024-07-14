/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.webeng.auleweb.data.dao.CorsoDAO;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.impl.proxy.CorsoProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;

public class CorsoDAO_MySql extends DAO implements CorsoDAO{

    private PreparedStatement sCorsoByID;
    private PreparedStatement sCorsiAll;
    
    private PreparedStatement iCorso;
    
    private PreparedStatement dCorso;
    
    
    public CorsoDAO_MySql(DataLayer d) {
        super(d);
    }
        
    @Override
    public void init() throws DataException{
        try{
            super.init();
            
            sCorsoByID = connection.prepareStatement("select * from corso where id = ?");
            sCorsiAll = connection.prepareStatement("select * from corso");

            iCorso = connection.prepareStatement("insert into corso (`nome`) values (?)", Statement.RETURN_GENERATED_KEYS);
            
            dCorso = connection.prepareStatement("delete from corso where id = ?");
            
        } catch (SQLException ex){
            throw new DataException("Error initializing Corso data layer", ex);
        } 
    }

    @Override
    public Corso createCorso() {
        return new CorsoProxy(getDataLayer());
    }
    
    private CorsoProxy createCorso(ResultSet rs) throws DataException{
        CorsoProxy c = (CorsoProxy) createCorso();
        try {
            c.setKey(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
        } catch (SQLException e) {
            throw new DataException("Unable to create Attrezzatura object form ResultSet", e);
        }
        return c;
    }

    @Override
    public Corso getCorso(int corso_key) throws DataException {
        Corso c = null;
        try{
            sCorsoByID.setInt(1, corso_key);
            try(ResultSet rs = sCorsoByID.executeQuery()){
                if (rs.next()){
                    c = createCorso(rs);
                    dataLayer.getCache().add(Corso.class, c);
                }
            }
        }catch(SQLException ex){
            throw new DataException("Unable to load Corso by id",ex);
        }
        return c;
    }

    @Override
    public List<Corso> getCorsi() throws DataException {
        List<Corso> corsi = new ArrayList<>();
        try (ResultSet rs = sCorsiAll.executeQuery()) {
            while (rs.next()) {
                corsi.add(getCorso(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load corsi", ex);
        }
        return corsi;
    }

    @Override
    public void storeCorso(Corso corso) throws DataException {
        try{
            iCorso.setString(1, corso.getNome());
            iCorso.executeUpdate();
        } catch (SQLException ex){
            throw new DataException("unable to store Corso", ex);
        }
    }

    @Override
    public void deleteCorso(Corso c) throws DataException {
        try {
            dCorso.setInt(1, c.getKey());
            dCorso.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Corso", ex);
        }
    }
    
}
