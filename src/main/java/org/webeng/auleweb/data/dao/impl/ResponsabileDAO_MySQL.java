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
import org.webeng.auleweb.data.dao.ResponsabileDAO;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.impl.proxy.ResponsabileProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

/**
 *
 * @author enric
 */
public class ResponsabileDAO_MySQL extends DAO implements ResponsabileDAO {

    private PreparedStatement sResponsabileByID, sResponsabileByEvento;

    private PreparedStatement sResponsabili, sUnassignedResponsabili, sResponsabileByKeyword;

    private PreparedStatement iResponsabile, uResponsabile, dResponsabile;

    public ResponsabileDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sResponsabileByID = connection.prepareStatement("SELECT * FROM responsabile WHERE ID=?");
            sResponsabileByEvento = connection.prepareStatement("SELECT responsabile.id FROM responsabile JOIN evento e ON e.id_responsabile = responsabile.ID WHERE e.id = ?");

            sResponsabili = connection.prepareStatement("SELECT id FROM responsabile");
            sUnassignedResponsabili = connection.prepareStatement("SELECT responsabile.id FROM responsabile LEFT JOIN evento ON evento.id_responsabile = responsabile.id WHERE evento.id_responsabile IS NULL");
            sResponsabileByKeyword = connection.prepareStatement("SELECT * FROM responsabile where nome like ?;");
            iResponsabile = connection.prepareStatement("INSERT INTO responsabile(`nome`, `email`) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            uResponsabile = connection.prepareStatement("UPDATE responsabile set nome=?, email=?, version=? WHERE id=? and version=?");
            dResponsabile = connection.prepareStatement("DELETE FROM responsabile WHERE id=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing responsabile data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sResponsabileByID.close();
            sResponsabileByEvento.close();

            sResponsabili.close();
            sUnassignedResponsabili.close();

            iResponsabile.close();
            uResponsabile.close();
            dResponsabile.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying responsabile prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Responsabile createResponsabile() {
        return new ResponsabileProxy(getDataLayer());
    }

    private ResponsabileProxy createResponsabile(ResultSet rs) throws DataException {
        ResponsabileProxy r = (ResponsabileProxy) createResponsabile();
        try {
            r.setKey(rs.getInt("id"));
            r.setNome(rs.getString("nome"));
            r.setEmail(rs.getString("email"));
            r.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create responsabile object form ResultSet", ex);
        }
        return r;
    }

    @Override
    public Responsabile getResponsabile(int responsabile_key) throws DataException {
        Responsabile r = null;
        if (dataLayer.getCache().has(Responsabile.class, responsabile_key)) {
            r = dataLayer.getCache().get(Responsabile.class, responsabile_key);
        } else {
            try {
                sResponsabileByID.setInt(1, responsabile_key);
                try (ResultSet rs = sResponsabileByID.executeQuery()) {
                    if (rs.next()) {
                        r = createResponsabile(rs);
                        dataLayer.getCache().add(Responsabile.class, r);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load responsabile by ID", ex);

            }
        }
        return r;
    }

    @Override
    public List<Responsabile> getResponsabili() throws DataException {
        List<Responsabile> result = new ArrayList();
        try (ResultSet rs = sResponsabili.executeQuery()) {
            while (rs.next()) {
                result.add((Responsabile) getResponsabile(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load responsabile", ex);
        }
        return result;
    }

    @Override
    public Responsabile getResponsabileByEvento(Evento evento) throws DataException {
        Responsabile r = null;
        try {
            sResponsabileByEvento.setInt(1, evento.getKey());
            try (ResultSet rs = sResponsabileByEvento.executeQuery()) {
                if (rs.next()) {
                    r = createResponsabile(rs);
                    dataLayer.getCache().add(Responsabile.class, r);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load responsabile by Evento", ex);
        }
        return r;
    }

    @Override
    public void storeResponsabile(Responsabile responsabile) throws DataException {
        try {
            if (responsabile.getKey() != null && responsabile.getKey() > 0) {
                if (responsabile instanceof DataItemProxy && !((DataItemProxy) responsabile).isModified()) {
                    return;
                }
                // UPDATE
                uResponsabile.setString(1, responsabile.getNome());
                uResponsabile.setString(2, responsabile.getEmail());

                long current_version = responsabile.getVersion();
                long next_version = current_version + 1;

                uResponsabile.setLong(3, next_version);
                // WHERE ID = ? AND VERSION = ?
                uResponsabile.setInt(4, responsabile.getKey());
                uResponsabile.setLong(5, current_version);

                if (uResponsabile.executeUpdate() == 0) {
                    throw new OptimisticLockException(responsabile);
                } else {
                    responsabile.setVersion(next_version);
                }
            } else { //INSERT
                iResponsabile.setString(1, responsabile.getNome());
                iResponsabile.setString(2, responsabile.getEmail());

                if (iResponsabile.executeUpdate() == 1) {
                    try (ResultSet keys = iResponsabile.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            responsabile.setKey(key);
                            dataLayer.getCache().add(Responsabile.class, responsabile);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (responsabile instanceof DataItemProxy) {
                ((DataItemProxy) responsabile).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store responsabile", ex);
        }
    }

    @Override
    public List<Responsabile> getUnassignedResponsabili() throws DataException {
        List<Responsabile> result = new ArrayList();

        try (ResultSet rs = sUnassignedResponsabili.executeQuery()) {
            while (rs.next()) {
                result.add((Responsabile) getResponsabile(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned responsabili", ex);
        }
        return result;
    }

    @Override
    public List<Responsabile> getResponsabiliBySearch(String keyword) throws DataException {
        List<Responsabile> result = new ArrayList();
        try {
            sResponsabileByKeyword.setString(1, keyword+"%");
            try (ResultSet rs = sResponsabileByKeyword.executeQuery()) {
                while (rs.next()) {
                    result.add((Responsabile) getResponsabile(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load responsabili by keyword", ex);
        }
        return result;

    }

}
