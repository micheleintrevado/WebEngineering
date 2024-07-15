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
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.impl.proxy.AulaProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;

/**
 *
 * @author enric
 */
public class AulaDAO_MySQL extends DAO implements AulaDAO {

    private PreparedStatement sAulaByID, sAulaByEvento, sAuleByResponsabile, sAuleByGruppo, sAuleByAttrezzatura, sAuleNoGruppo;

    private PreparedStatement sAule, sUnassignedAule;

    private PreparedStatement iAula, uAula, dAula;

    public AulaDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sAulaByID = connection.prepareStatement("SELECT * FROM aula WHERE ID=?");
            sAulaByEvento = connection.prepareStatement("SELECT aula.id FROM aula JOIN evento e ON e.id_aula = aula.ID WHERE e.id = ?");
            sAuleByResponsabile = connection.prepareStatement("Select aula.id FROM AULA WHERE aula.id_responsabile = ?");
            sAuleByGruppo = connection.prepareStatement("SELECT aula.id FROM aula JOIN aula_gruppo ag ON ag.id_aula = aula.ID WHERE ag.id_gruppo = ?");
            sAuleByAttrezzatura = connection.prepareStatement("SELECT aula.id FROM aula JOIN aula_attrezzatura a_at on a_at.id_aula = aula.ID WHERE a_at.id_attrezzatura = ?");
            sAuleNoGruppo = connection.prepareStatement("SELECT aula.id FROM aula LEFT JOIN aula_gruppo ON aula.id = aula_gruppo.id_aula WHERE aula_gruppo.id_gruppo IS NULL;");

            sAule = connection.prepareStatement("SELECT id FROM aula");
            sUnassignedAule = connection.prepareStatement("SELECT aula.id FROM aula LEFT JOIN evento ON evento.id_aula = aula.id WHERE evento.id_aula IS NULL");

            iAula = connection.prepareStatement("INSERT INTO aula(`nome`, `luogo`, `edificio`, `piano`, `capienza`, `prese_elettriche`, `prese_rete`, `note`, `id_responsabile`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uAula = connection.prepareStatement("UPDATE aula set nome=?, luogo=?, edificio=?,piano=?,capienza=?,prese_elettriche=?,prese_rete=?,note=?, id_responsabile=?, version=? WHERE id=? and version=?");
            dAula = connection.prepareStatement("DELETE FROM aula WHERE id=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing aula data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sAulaByID.close();
            sAulaByEvento.close();
            sAuleByResponsabile.close();
            sAuleByGruppo.close();
            sAuleByAttrezzatura.close();
            sAuleNoGruppo.close();

            sAule.close();
            sUnassignedAule.close();

            iAula.close();
            uAula.close();
            dAula.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying aula prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Aula createAula() {
        return new AulaProxy(getDataLayer());
    }

    private AulaProxy createAula(ResultSet rs) throws DataException {
        AulaProxy a = (AulaProxy) createAula();
        try {
            a.setKey(rs.getInt("id"));
            a.setResponsabileKey(rs.getInt("id_responsabile"));
            a.setNome(rs.getString("nome"));
            a.setLuogo(rs.getString("luogo"));
            a.setEdificio(rs.getString("edificio"));
            a.setPiano(rs.getString("piano"));
            a.setCapienza(rs.getInt("capienza"));
            a.setPreseElettriche(rs.getInt("prese_elettriche"));
            a.setPreseRete(rs.getInt("prese_rete"));
            a.setNote(rs.getString("note"));
            a.setVersion(rs.getLong("version"));
            // TODO: a.setAttrezzature(attrezzatura);
            // TODO: a.setGruppi(Gruppo)
        } catch (SQLException ex) {
            throw new DataException("Unable to create aula object form ResultSet", ex);
        }
        return a;
    }

    @Override
    public Aula getAula(int aula_key) throws DataException {
        Aula a = null;
        if (dataLayer.getCache().has(Aula.class, aula_key)) {
            a = dataLayer.getCache().get(Aula.class, aula_key);
        } else {
            try {
                sAulaByID.setInt(1, aula_key);
                try (ResultSet rs = sAulaByID.executeQuery()) {
                    if (rs.next()) {
                        a = createAula(rs);
                        dataLayer.getCache().add(Aula.class, a);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load aula by ID", ex);

            }
        }
        return a;
    }

    @Override
    public List<Aula> getAule() throws DataException {
        List<Aula> result = new ArrayList();
        try (ResultSet rs = sAule.executeQuery()) {
            while (rs.next()) {
                result.add((Aula) getAula(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule", ex);
        }
        return result;
    }

    @Override
    public Aula getAulaByEvento(Evento evento) throws DataException {
        Aula a = null;
        try {
            sAulaByEvento.setInt(1, evento.getKey());
            try (ResultSet rs = sAulaByEvento.executeQuery()) {
                if (rs.next()) {
                    a = createAula(rs);
                    dataLayer.getCache().add(Aula.class, a);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aula by Evento", ex);
        }
        return a;
    }

    @Override
    public List<Aula> getAuleByResponsabile(Responsabile responsabile) throws DataException {
        List<Aula> result = new ArrayList();
        try {
            sAuleByResponsabile.setInt(1, responsabile.getKey());
            try (ResultSet rs = sAuleByResponsabile.executeQuery()) {
                while (rs.next()) {
                    result.add((Aula) getAula(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule by responsabile", ex);
        }
        return result;
    }

    @Override
    public List<Aula> getAuleByGruppo(Gruppo gruppo) throws DataException {
        List<Aula> result = new ArrayList();
        try {
            sAuleByGruppo.setInt(1, gruppo.getKey());
            try (ResultSet rs = sAuleByGruppo.executeQuery()) {
                while (rs.next()) {
                    result.add((Aula) getAula(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule by gruppo", ex);
        }
        return result;
    }

    @Override
    public List<Aula> getAuleSenzaGruppo() throws DataException {
        List<Aula> result = new ArrayList();
        try (ResultSet rs = sAuleByResponsabile.executeQuery()) {
            while (rs.next()) {
                result.add((Aula) getAula(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule with no gruppo", ex);
        }
        return result;
    }

    @Override
    public List<Aula> getUnassignedAule() throws DataException {
        List<Aula> result = new ArrayList();

        try (ResultSet rs = sUnassignedAule.executeQuery()) {
            while (rs.next()) {
                result.add((Aula) getAula(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load unassigned aule", ex);
        }
        return result;
    }

    @Override
    public void storeAula(Aula aula) throws DataException {
        try {
            if (aula.getKey() != null && aula.getKey() > 0) {
                if (aula instanceof DataItemProxy && !((DataItemProxy) aula).isModified()) {
                    return;
                }
                // UPDATE
                uAula.setString(1, aula.getNome());
                uAula.setString(2, aula.getLuogo());
                uAula.setString(3, aula.getEdificio());
                uAula.setString(4, aula.getPiano());
                uAula.setInt(5, (int) aula.getCapienza());
                uAula.setInt(6, (int) aula.getPreseElettriche());
                uAula.setInt(7, (int) aula.getPreseRete());
                uAula.setString(8, aula.getNote());
                if (aula.getResponsabile() != null) {
                    uAula.setInt(9, (int) aula.getResponsabile().getKey());
                } else {
                    uAula.setNull(3, java.sql.Types.INTEGER);
                }

                long current_version = aula.getVersion();
                long next_version = current_version + 1;

                uAula.setLong(10, next_version);
                // WHERE ID = ? AND VERSION = ?
                uAula.setInt(11, aula.getKey());
                uAula.setLong(12, current_version);

                if (uAula.executeUpdate() == 0) {
                    throw new OptimisticLockException(aula);
                } else {
                    aula.setVersion(next_version);
                }
            } else { //INSERT
                iAula.setString(1, aula.getNome());
                iAula.setString(2, aula.getLuogo());
                iAula.setString(3, aula.getEdificio());
                iAula.setString(4, aula.getPiano());
                iAula.setInt(5, (int) aula.getCapienza());
                iAula.setInt(6, (int) aula.getPreseElettriche());
                iAula.setInt(7, (int) aula.getPreseRete());
                iAula.setString(8, aula.getNote());
                if (aula.getResponsabile() != null) {
                    iAula.setInt(9, (int) aula.getResponsabile().getKey());
                } else {
                    iAula.setNull(3, java.sql.Types.INTEGER);
                }

                if (iAula.executeUpdate() == 1) {
                    try (ResultSet keys = iAula.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            aula.setKey(key);
                            dataLayer.getCache().add(Aula.class, aula);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (aula instanceof DataItemProxy) {
                ((DataItemProxy) aula).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store aula", ex);
        }
    }

    @Override
    public List<Aula> getAuleByAttrezzatura(Attrezzatura attrezzatura) throws DataException {
        List<Aula> result = new ArrayList();
        try {
            sAuleByAttrezzatura.setInt(1, attrezzatura.getKey());
            try (ResultSet rs = sAuleByAttrezzatura.executeQuery()) {
                while (rs.next()) {
                    result.add((Aula) getAula(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load aule by attrezzatura", ex);
        }
        return result;
    }
}
