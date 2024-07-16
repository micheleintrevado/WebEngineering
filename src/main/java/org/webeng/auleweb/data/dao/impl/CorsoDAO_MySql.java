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
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

public class CorsoDAO_MySQL extends DAO implements CorsoDAO{

    private PreparedStatement sCorsoByID;
    private PreparedStatement sCorsiAll;
    
    private PreparedStatement iCorso;
    
    private PreparedStatement uCorso;
    
    private PreparedStatement dCorso;
    
    
    public CorsoDAO_MySQL(DataLayer d) {
        super(d);
    }
        
    @Override
    public void init() throws DataException{
        try{
            super.init();
            
            sCorsoByID = connection.prepareStatement("select * from corso where id = ?");
            sCorsiAll = connection.prepareStatement("select * from corso");

            iCorso = connection.prepareStatement("insert into corso (`nome`) values (?)", Statement.RETURN_GENERATED_KEYS);
            
            uCorso = connection.prepareStatement("update corso set nome=?, version=? where id=? and version=?");
            
            dCorso = connection.prepareStatement("delete from corso where id = ?");
        } catch (SQLException ex){
            throw new DataException("Error initializing Corso data layer", ex);
        } 
    }
    
    @Override
    public void destroy() throws DataException {
        try{
            
            sCorsoByID.close();
            sCorsiAll.close();

            iCorso.close();
            
            uCorso.close();
            
            dCorso.close();
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
        try {
            if (corso.getKey() != null && corso.getKey() > 0) {
                if (corso instanceof DataItemProxy && !((DataItemProxy) corso).isModified()) {
                    return;
                }
                // UPDATE
                uCorso.setString(1, corso.getNome());

                long current_version = corso.getVersion();
                long next_version = current_version + 1;

                uCorso.setLong(2, next_version);
                // WHERE ID = ? AND VERSION = ?
                uCorso.setInt(3, corso.getKey());
                uCorso.setLong(4, current_version);

                if (uCorso.executeUpdate() == 0) {
                    throw new OptimisticLockException(corso);
                } else {
                    corso.setVersion(next_version);
                }
            } else { //INSERT
                iCorso.setString(1, corso.getNome());

                if (iCorso.executeUpdate() == 1) {
                    try (ResultSet keys = iCorso.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            corso.setKey(key);
                            dataLayer.getCache().add(Corso.class, corso);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (corso instanceof DataItemProxy) {
                ((DataItemProxy) corso).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store corso", ex);
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
