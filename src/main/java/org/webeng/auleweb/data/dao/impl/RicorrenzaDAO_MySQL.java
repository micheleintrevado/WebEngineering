/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.webeng.auleweb.data.dao.RicorrenzaDAO;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoRicorrenza;
import org.webeng.auleweb.data.model.impl.proxy.RicorrenzaProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

/**
 *
 * @author miche
 */
public class RicorrenzaDAO_MySQL extends DAO implements RicorrenzaDAO {

    private PreparedStatement sRicorrenzaById;
    private PreparedStatement sRicorrenzaByEvento;

    private PreparedStatement iRicorrenza;

    public RicorrenzaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sRicorrenzaById = connection.prepareStatement("select * from ricorrenza where id =?");
            sRicorrenzaByEvento = connection.prepareStatement("SELECT * FROM ricorrenza WHERE id=?");

            iRicorrenza = connection.prepareStatement("insert into ricorrenza (`tipo`,`data_termine`) values (?,?);", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException ex) {
            throw new DataException("Error initializing Ricorrenza data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sRicorrenzaById.close();
            sRicorrenzaByEvento.close();

            iRicorrenza.close();
        } catch (SQLException ex) {
            throw new DataException("Error initializing Ricorrenza data layer", ex);
        }
    }

    @Override
    public Ricorrenza createRicorrenza() {
        return new RicorrenzaProxy(getDataLayer());
    }

    private RicorrenzaProxy createRicorrenza(ResultSet rs) throws DataException {
        RicorrenzaProxy r = (RicorrenzaProxy) createRicorrenza();
        try {
            r.setKey(rs.getInt("id"));
            r.setTipoRicorrenza(TipoRicorrenza.valueOf(rs.getString("tipo")));
            r.setDataTermine(rs.getDate("data_termine"));
        } catch (SQLException e) {
            throw new DataException("Unable to create Ricorrenza object form ResultSet", e);
        }
        return r;
    }

    @Override
    public Ricorrenza getRicorrenza(int ricorrenza_key) throws DataException {
        Ricorrenza r = null;
        try {
            sRicorrenzaById.setInt(1, ricorrenza_key);
            try (ResultSet rs = sRicorrenzaById.executeQuery()) {
                if (rs.next()) {
                    r = createRicorrenza(rs);
                    dataLayer.getCache().add(Ricorrenza.class, r);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load ricorrenza by id", ex);
        }
        return r;
    }

    @Override
    public Ricorrenza getRicorrenzaByEvento(Evento evento) throws DataException {
        Ricorrenza r = null;
        try {
            if (evento.getRicorrenza() != null) {
                sRicorrenzaByEvento.setInt(1, evento.getRicorrenza().getKey());
                try (ResultSet rs = sRicorrenzaByEvento.executeQuery()) {
                    if (rs.next()) {
                        r = createRicorrenza(rs);
                        dataLayer.getCache().add(Ricorrenza.class, r);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load ricorrenza by evento", ex);
        }
        return r;
    }

    @Override
    public void storeRicorrenza(Ricorrenza ricorrenza) throws DataException {
        try {
            if (ricorrenza instanceof DataItemProxy && !((DataItemProxy) ricorrenza).isModified()) {
                return;
            }
            iRicorrenza.setString(1, ricorrenza.getTipoRicorrenza().toString());
            iRicorrenza.setDate(2, ricorrenza.getDataTermine());

            if (iRicorrenza.executeUpdate() == 1) {
                try (ResultSet keys = iRicorrenza.getGeneratedKeys()) {
                    if (keys.next()) {
                        int key = keys.getInt(1);
                        ricorrenza.setKey(key);
                        dataLayer.getCache().add(Ricorrenza.class, ricorrenza);
                    }
                }

                // RESET dell'attributo modified
                if (ricorrenza instanceof DataItemProxy) {
                    ((DataItemProxy) ricorrenza).setModified(false);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to store ricorrenza", ex);
        }
    }

}
