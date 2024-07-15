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
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class GruppoDAO_MySql extends DAO implements GruppoDAO {

    private PreparedStatement sGruppoByID;
    private PreparedStatement sGruppoAll;
    private PreparedStatement sGruppiByAula;
    private PreparedStatement sUnassignedGruppi;

    private PreparedStatement iGruppo;
    private PreparedStatement assignGruppoAula;

    private PreparedStatement dGruppo;

    public GruppoDAO_MySql(DataLayer d) {
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
