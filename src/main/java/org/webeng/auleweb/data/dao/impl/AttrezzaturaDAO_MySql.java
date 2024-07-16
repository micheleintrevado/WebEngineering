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
import org.webeng.auleweb.data.dao.AttrezzaturaDAO;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.impl.proxy.AttrezzaturaProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

/**
 *
 * @author miche
 */
public class AttrezzaturaDAO_MySQL extends DAO implements AttrezzaturaDAO{

    private PreparedStatement sAttrezzaturaById;
    private PreparedStatement sAttrezzaturaAll;
    private PreparedStatement sAttrezzaturaByAula;
    
    private PreparedStatement iAttrezzatura;
    private PreparedStatement iAssignAttrezzatura;
    
    private PreparedStatement uAttrezzatura;
    
    private PreparedStatement dAttrezzatura;
    
    public AttrezzaturaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException{
        try{
            super.init();
            sAttrezzaturaById = connection.prepareStatement("select * from attrezzatura where id = ?");
            sAttrezzaturaAll = connection.prepareStatement("select * from attrezzatura");
            sAttrezzaturaByAula = connection.prepareStatement("SELECT * FROM attrezzatura as a join aula_attrezzatura as attr on a.id = attr.id_aula where attr.id_aula = ?;");
            
            iAttrezzatura = connection.prepareStatement("insert into webeng.attrezzatura(`tipo`) values (?);", Statement.RETURN_GENERATED_KEYS);
            iAssignAttrezzatura = connection.prepareStatement("insert into webeng.aula_attrezzatura(`id_aula`,`id_attrezzatura`) values (?,?);");
            
            uAttrezzatura = connection.prepareStatement("UPDATE attrezzatura set tipo=?,version=? WHERE id=? and version=?");
            
            dAttrezzatura = connection.prepareStatement("delete from webeng.attrezzatura where id = ?;");
        } catch (SQLException ex){
            throw new DataException("Error initializing attrezzatura data layer", ex);
        } 
    }
    
    @Override
    public void destroy() throws DataException {
        try{
            sAttrezzaturaById.close();
            sAttrezzaturaAll.close();
            sAttrezzaturaByAula.close();
            
            iAttrezzatura.close();
            iAssignAttrezzatura.close();
            
            dAttrezzatura.close();
        } catch (SQLException ex){
            throw new DataException("Error initializing attrezzatura data layer", ex);
        }
    }

    @Override
    public Attrezzatura createAttrezzatura() {
        return new AttrezzaturaProxy(getDataLayer());
    }
    
    private AttrezzaturaProxy createAttrezzatura(ResultSet rs) throws DataException{
        AttrezzaturaProxy a = (AttrezzaturaProxy) createAttrezzatura();
        try {
            a.setKey(rs.getInt("id"));
            a.setTipo(rs.getString("tipo"));
        } catch (SQLException e) {
            throw new DataException("Unable to create Attrezzatura object form ResultSet", e);
        }
        return a;
    }

    @Override
    public Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException {
        Attrezzatura a = null;
        try{
            sAttrezzaturaById.setInt(1, attrezzatura_key);
            try(ResultSet rs = sAttrezzaturaById.executeQuery()){
                if (rs.next()){
                    a = createAttrezzatura(rs);
                    dataLayer.getCache().add(Attrezzatura.class, a);
                }
            }
        }catch(SQLException ex){
            throw new DataException("Unable to load Attrezzatura by filename",ex);
        }
        return a;
    }

    @Override
    public List<Attrezzatura> getAttrezzatura() throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try (ResultSet rs = sAttrezzaturaAll.executeQuery()) {
            while (rs.next()) {
                attrezzature.add(getAttrezzatura(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load attrezzature", ex);
        }
        return attrezzature;
    }

    @Override
    public List<Attrezzatura> getAttrezzaturaByAula(Aula aula) throws DataException {
        List<Attrezzatura> attrezzature = new ArrayList<>();
        try (ResultSet rs = sAttrezzaturaByAula.executeQuery()) {
            while (rs.next()) {
                attrezzature.add(getAttrezzatura(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load attrezzature filtered by aula", ex);
        }
        return attrezzature;
    }

    // TODO: controllare l'id dell'aula
    @Override
    public void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException {
        try {
            if (attrezzatura.getKey() != null && attrezzatura.getKey() > 0) {
                if (attrezzatura instanceof DataItemProxy && !((DataItemProxy) attrezzatura).isModified()) {
                    return;
                }
                // UPDATE
                uAttrezzatura.setString(1, attrezzatura.getTipo());

                long current_version = attrezzatura.getVersion();
                long next_version = current_version + 1;

                uAttrezzatura.setLong(2, next_version);
                // WHERE ID = ? AND VERSION = ?
                uAttrezzatura.setInt(3, attrezzatura.getKey());
                uAttrezzatura.setLong(12, current_version);

                if (uAttrezzatura.executeUpdate() == 0) {
                    throw new OptimisticLockException(attrezzatura);
                } else {
                    attrezzatura.setVersion(next_version);
                }
            } else { //INSERT
                iAttrezzatura.setString(1, attrezzatura.getTipo());

                if (iAttrezzatura.executeUpdate() == 1) {
                    try (ResultSet keys = iAttrezzatura.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            attrezzatura.setKey(key);
                            dataLayer.getCache().add(Attrezzatura.class, attrezzatura);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (attrezzatura instanceof DataItemProxy) {
                ((DataItemProxy) attrezzatura).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store attrezzatura", ex);
        }
    }

    @Override
    public void assignAttrezzatura(Attrezzatura attrezzatura, Aula aula) throws DataException {
        try{
            iAssignAttrezzatura.setInt(1, aula.getKey());
            iAssignAttrezzatura.setInt(2, attrezzatura.getKey());
            iAssignAttrezzatura.executeUpdate();
        } catch (SQLException ex){
            throw new DataException("unable to assign Attrezzatura to Aula", ex);
        }
    }
    
    @Override
    public void deleteAttrezzatura(Attrezzatura a) throws DataException {
        try {
            dAttrezzatura.setInt(1, a.getKey());
            dAttrezzatura.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Attrezzatura", ex);
        }
    }
}
