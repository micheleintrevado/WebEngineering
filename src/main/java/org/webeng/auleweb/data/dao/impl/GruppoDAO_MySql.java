/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.webeng.auleweb.data.dao.GruppoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.impl.proxy.GruppoProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

/**
 *
 * @author miche
 */
public class GruppoDAO_MySQL extends DAO implements GruppoDAO {
    
    private PreparedStatement sGruppoByID;
    private PreparedStatement sGruppoAll;
    private PreparedStatement sGruppiByAula;
    private PreparedStatement sUnassignedGruppi;

    private PreparedStatement iGruppo;
    private PreparedStatement assignGruppoAula;
    
    private PreparedStatement uGruppo;

    private PreparedStatement dGruppo;

    public GruppoDAO_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            sGruppoAll = connection.prepareStatement("select * from gruppi");
            sGruppoByID = connection.prepareStatement("select * from gruppo where id = ?");
            sGruppiByAula = connection.prepareStatement("select * from gruppo as g join aula_gruppo as ag on g.id = ag.id_gruppo join aula as a on a.id = ag.id_aula where a.id = ?");
            sUnassignedGruppi = connection.prepareStatement("select * from gruppo as g left join aula_gruppo as ag on g.id = ag.id_gruppo where ag.id_aula is null");

            iGruppo = connection.prepareStatement("insert into webeng.gruppo(`nome`,`descrizione`) values (?,?)");
            assignGruppoAula = connection.prepareStatement("insert into webeng.aula_gruppo(`id_gruppo`,`id_aula`) values (?,?);");

            uGruppo = connection.prepareStatement("UPDATE gruppo set nome=?, descrizione=?, version=? WHERE id=? and version=?;");
            
            dGruppo = connection.prepareStatement("delete from gruppo where id = ?");

        } catch (SQLException ex) {
            throw new DataException("Error initializing attrezzatura data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sGruppoByID.close();
            sGruppiByAula.close();
            sUnassignedGruppi.close();

            iGruppo.close();
            assignGruppoAula.close();

            dGruppo.close();

        } catch (SQLException ex) {
            throw new DataException("Error ddestroying gruppo data layer", ex);
        }
    }

    @Override
    public Gruppo createGruppo() {
        return new GruppoProxy(getDataLayer());
    }

    private GruppoProxy createGruppo(ResultSet rs) throws DataException {
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
        Gruppo g = null;
        if (dataLayer.getCache().has(Gruppo.class, gruppo_key)) {
            g = dataLayer.getCache().get(Gruppo.class, gruppo_key);
        } else {
            try {
                sGruppoByID.setInt(1, gruppo_key);
                try (ResultSet rs = sGruppoByID.executeQuery()) {
                    if (rs.next()) {
                        g = createGruppo(rs);
                        dataLayer.getCache().add(Gruppo.class, g);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load gruppo by ID", ex);

            }
        }
        return g;
    }

    @Override
    public List<Gruppo> getGruppi() throws DataException {
        List<Gruppo> result = new ArrayList();
        try (ResultSet rs = sGruppoAll.executeQuery()) {
            while (rs.next()) {
                result.add((Gruppo) getGruppo(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load gruppi", ex);
        }
        return result;
    }

    @Override
    public List<Gruppo> getGruppiByAula(Aula aula) throws DataException {
        List<Gruppo> result = new ArrayList();
        try {
            sUnassignedGruppi.setInt(1, aula.getKey());
            try (ResultSet rs = sUnassignedGruppi.executeQuery()) {
                while (rs.next()) {
                    result.add((Gruppo) getGruppo(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load gruppi filtered by aula", ex);
        }
        return result;
    }

    @Override
    public List<Gruppo> getUnassignedGruppi() throws DataException {
        List<Gruppo> result = new ArrayList();
        try (ResultSet rs = sUnassignedGruppi.executeQuery()) {
            while (rs.next()) {
                result.add((Gruppo) getGruppo(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned gruppi", ex);
        }
        return result;
    }

    @Override
    public void storeGruppo(Gruppo gruppo) throws DataException {
        try {
            if (gruppo.getKey() != null && gruppo.getKey() > 0) {
                if (gruppo instanceof DataItemProxy && !((DataItemProxy) gruppo).isModified()) {
                    return;
                }
                // UPDATE
                uGruppo.setString(1, gruppo.getNome());
                uGruppo.setString(2, gruppo.getDescrizione());
                
                long current_version = gruppo.getVersion();
                long next_version = current_version + 1;

                uGruppo.setLong(10, next_version);
                // WHERE ID = ? AND VERSION = ?
                uGruppo.setInt(11, gruppo.getKey());
                uGruppo.setLong(12, current_version);

                if (uGruppo.executeUpdate() == 0) {
                    throw new OptimisticLockException(gruppo);
                } else {
                    gruppo.setVersion(next_version);
                }
            } else { //INSERT
                iGruppo.setString(1, gruppo.getNome());
                iGruppo.setString(2, gruppo.getDescrizione());


                if (iGruppo.executeUpdate() == 1) {
                    try (ResultSet keys = iGruppo.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            gruppo.setKey(key);
                            dataLayer.getCache().add(Gruppo.class, gruppo);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (gruppo instanceof DataItemProxy) {
                ((DataItemProxy) gruppo).setModified(false);
            }
        } catch (SQLException ex/*| OptimisticLockException ex*/) {
            throw new DataException("Unable to store gruppo", ex);
        }
    }

    @Override
    public void assignGruppo(Gruppo gruppo, Aula aula) throws DataException {
        try{
            iGruppo.setInt(1, gruppo.getKey());
            iGruppo.setInt(1, aula.getKey());
            iGruppo.executeUpdate();
        } catch (SQLException ex){
            throw new DataException("unable to assign aula to gruppo", ex);
        }
    }

    @Override
    public void deleteGruppo(Gruppo g) throws DataException {
        try {
            dGruppo.setInt(1, g.getKey());
            dGruppo.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete grupo", ex);
        }
    }
    
    

}
