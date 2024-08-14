/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoEvento;
import org.webeng.auleweb.data.model.TipoRicorrenza;
import org.webeng.auleweb.data.model.impl.EventoImpl;
import org.webeng.auleweb.data.model.impl.proxy.EventoProxy;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.framework.data.OptimisticLockException;
import org.webeng.auleweb.framework.utils.ServletHelpers;

/**
 *
 * @author enric
 */
public class EventoDAO_MySQL extends DAO implements EventoDAO {

    private PreparedStatement sEventoByID, sEventiByAula, sEventiByResponsabile, sEventiByCorso,
            sEventiByRicorrenza, sEventiByGiorno, sEventiByAulaAndGiorno, sEventiSovrapposti,
            sEventiBySettimanaAula, sEventiBySettimanaCorso,
            sEventiAttuali, sEventiProssimi;

    private PreparedStatement sEventiRangeCorso;
    private PreparedStatement sEventiRange;
    private PreparedStatement sEventi;

    private PreparedStatement iEvento, uEvento, dEvento;
    private PreparedStatement uEventiRicorrenti, dEventiRicorrenti;
    private PreparedStatement checkEventi, checkEventi2, getEventiNonInseriti;

    public EventoDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sEventoByID = connection.prepareStatement("SELECT * FROM evento WHERE ID=?");
            sEventiByAula = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula=?");
            sEventiByResponsabile = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_responsabile = ?");
            sEventiByCorso = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ?");
            sEventiByRicorrenza = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_master = ?");
            sEventiByGiorno = connection.prepareStatement("SELECT id FROM evento WHERE evento.giorno = ?"); // GIORNO = '2024-05-14'
            sEventiByAulaAndGiorno = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula = ? AND evento.giorno = ?"); // GIORNO = '2024-05-14'
            sEventiSovrapposti = connection.prepareStatement("SELECT * FROM evento WHERE id <> ? AND id_aula = ? AND giorno = ? AND ((orario_inizio < ? AND orario_fine > ?))");

            sEventiBySettimanaAula = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula = ? and evento.giorno BETWEEN ? AND date_add(?, interval 1 week)");
            sEventiBySettimanaCorso = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ? and evento.giorno BETWEEN ? AND date_add(?, interval 1 week)");
            sEventiAttuali = connection.prepareStatement("SELECT id FROM evento WHERE giorno = date(now()) AND time(now()) BETWEEN orario_inizio AND orario_fine");
            sEventiProssimi = connection.prepareStatement("SELECT id FROM evento WHERE TIMESTAMP(giorno, orario_inizio) BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? HOUR)"); // Default 3 ore

            sEventiRangeCorso = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ? AND giorno between ? AND ?");
            sEventiRange = connection.prepareStatement("SELECT id FROM evento WHERE giorno between ? AND ?");

            sEventi = connection.prepareStatement("SELECT id FROM evento");

            iEvento = connection.prepareStatement("INSERT INTO evento(nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_master, id_aula, id_corso) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uEvento = connection.prepareStatement("UPDATE evento set nome=?, giorno=?, orario_inizio=?,orario_fine=?,descrizione=?,tipologia=?,id_responsabile=?,id_master=?,id_aula=?,id_corso=?,version=? WHERE id=? and version=?");
            dEvento = connection.prepareStatement("DELETE FROM evento WHERE id=?");

            uEventiRicorrenti = connection.prepareStatement("UPDATE evento set nome=?, orario_inizio=?,orario_fine=?,descrizione=?,tipologia=?,id_responsabile=?,id_aula=?,id_corso=?,id_master=?,version=? WHERE id_master=? AND giorno >= ?"); // se si vogliono modificare solo quelli successivi
            dEventiRicorrenti = connection.prepareStatement("DELETE FROM evento WHERE id_master=? AND giorno > ?");
            checkEventi = connection.prepareStatement("DELETE e FROM Evento e JOIN Ricorrenza r ON e.id_master = r.id WHERE e.giorno > r.data_termine");
            checkEventi2 = connection.prepareStatement("DELETE e FROM Evento e JOIN ( SELECT MIN(id) AS id, giorno, orario_inizio, orario_fine, id_aula FROM Evento GROUP BY giorno, orario_inizio, orario_fine, id_aula ) AS keep on e.giorno = keep.giorno AND e.id_aula = keep.id_aula AND (\n"
                    + "(e.orario_inizio BETWEEN keep.orario_inizio AND keep.orario_fine)\n"
                    + "       OR (e.orario_fine BETWEEN keep.orario_inizio AND keep.orario_fine)\n"
                    + "       OR (keep.orario_inizio BETWEEN e.orario_inizio AND e.orario_fine)\n"
                    + "       OR (keep.orario_fine BETWEEN e.orario_inizio AND e.orario_fine)) WHERE e.id > keep.id;");
            getEventiNonInseriti = connection.prepareStatement("SELECT e.id, e.giorno, e.id_aula FROM Evento e JOIN ( SELECT MIN(id) AS id, giorno, orario_inizio, orario_fine, id_aula FROM Evento GROUP BY giorno, orario_inizio, orario_fine, id_aula ) AS keep on e.giorno = keep.giorno AND e.id_aula = keep.id_aula AND (\n"
                    + "(e.orario_inizio BETWEEN keep.orario_inizio AND keep.orario_fine)\n"
                    + "       OR (e.orario_fine BETWEEN keep.orario_inizio AND keep.orario_fine)\n"
                    + "       OR (keep.orario_inizio BETWEEN e.orario_inizio AND e.orario_fine)\n"
                    + "       OR (keep.orario_fine BETWEEN e.orario_inizio AND e.orario_fine)) WHERE e.id > keep.id;");
            checkEventi();
        } catch (SQLException ex) {
            throw new DataException("Error initializing evento data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sEventoByID.close();
            sEventiByAula.close();
            sEventiByResponsabile.close();
            sEventiByCorso.close();
            sEventiByRicorrenza.close();
            sEventiByGiorno.close();
            sEventiByAulaAndGiorno.close();
            sEventiBySettimanaAula.close();
            sEventiBySettimanaCorso.close();
            sEventiAttuali.close();
            sEventiProssimi.close();

            sEventiRangeCorso.close();
            sEventi.close();

            iEvento.close();
            uEvento.close();
            dEvento.close();
        } catch (SQLException ex) {
            throw new DataException("Error destroying aula prepared statements", ex);
        }
        super.destroy();
    }

    @Override
    public Evento createEvento() {
        return new EventoProxy(getDataLayer());
    }

    private EventoProxy createEvento(ResultSet rs) throws DataException {
        checkEventi();
        EventoProxy e = (EventoProxy) createEvento();
        try {
            e.setKey(rs.getInt("id"));
            e.setResponsabileKey(rs.getInt("id_responsabile"));
            e.setNome(rs.getString("nome"));
            e.setGiorno(rs.getDate("giorno"));
            e.setOrarioInizio(rs.getTime("orario_inizio"));
            e.setOrarioFine(rs.getTime("orario_fine"));
            e.setDescrizione(rs.getString("descrizione"));
            e.setTipoEvento(TipoEvento.valueOf(rs.getString("tipologia")));
            e.setRicorrenzeKey(rs.getInt("id_master"));
            e.setAulaKey(rs.getInt("id_aula"));
            e.setCorsoKey(rs.getInt("id_corso"));
            e.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create evento object form ResultSet", ex);
        }
        return e;
    }

    @Override
    public Evento getEvento(int evento_key) throws DataException {
        checkEventi();
        Evento e = null;
        if (dataLayer.getCache().has(Evento.class, evento_key)) {
            e = dataLayer.getCache().get(Evento.class, evento_key);
        } else {
            try {
                sEventoByID.setInt(1, evento_key);
                try (ResultSet rs = sEventoByID.executeQuery()) {
                    if (rs.next()) {
                        e = createEvento(rs);
                        dataLayer.getCache().add(Evento.class, e);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load evento by ID", ex);

            }
        }
        return e;
    }

    @Override
    public List<Evento> getEventi() throws DataException {
        List<Evento> result = new ArrayList();
        try (ResultSet rs = sEventi.executeQuery()) {
            while (rs.next()) {
                result.add((Evento) getEvento(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiByAula(Aula aula) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByAula.setInt(1, aula.getKey());
            try (ResultSet rs = sEventiByAula.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by aula", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiByResponsabile(Responsabile responsabile) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByResponsabile.setInt(1, responsabile.getKey());
            try (ResultSet rs = sEventiByResponsabile.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by responsabile", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiByCorso(Corso corso) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByCorso.setInt(1, corso.getKey());
            try (ResultSet rs = sEventiByCorso.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by Corso", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiGiorno(Date giorno) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            // TODO: CHECK TYPE
            System.out.println("CHECK TYPE in method getEventiGiorno");
            var giornoSQL = new java.sql.Date(giorno.getTime());
            sEventiByGiorno.setDate(1, giornoSQL);
            try (ResultSet rs = sEventiByGiorno.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by giorno", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiAulaGiorno(Aula aula, Date giorno) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByAulaAndGiorno.setInt(1, aula.getKey());
            sEventiByAulaAndGiorno.setDate(2, new java.sql.Date(giorno.getTime()));
            try (ResultSet rs = sEventiByAulaAndGiorno.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by aula and giorno", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiSettimana(Aula aula, Date settimana) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiBySettimanaAula.setInt(1, aula.getKey());
            sEventiBySettimanaAula.setDate(2, new java.sql.Date(settimana.getTime()));
            sEventiBySettimanaAula.setDate(3, new java.sql.Date(settimana.getTime()));
            try (ResultSet rs = sEventiBySettimanaAula.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by aula and settimana", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiSettimana(Corso corso, Date settimana) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiBySettimanaCorso.setInt(1, corso.getKey());
            sEventiBySettimanaCorso.setDate(2, new java.sql.Date(settimana.getTime()));
            sEventiBySettimanaCorso.setDate(3, new java.sql.Date(settimana.getTime()));
            try (ResultSet rs = sEventiBySettimanaCorso.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by corso and settimana", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiAttuali() throws DataException {
        List<Evento> result = new ArrayList();
        try (ResultSet rs = sEventiAttuali.executeQuery()) {
            while (rs.next()) {
                result.add((Evento) getEvento(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi attuali", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiProssimi(int prossime_ore) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiProssimi.setInt(1, prossime_ore);
            try (ResultSet rs = sEventiProssimi.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi prossimi", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiRicorrenti(Ricorrenza ricorrenza) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiByRicorrenza.setInt(1, ricorrenza.getKey());
            try (ResultSet rs = sEventiByRicorrenza.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by ricorrenza", ex);
        }
        return result;
    }

    @Override
    public List<Evento> getEventiRangeCorso(Date periodoStart, Date periodoEnd, Corso corso) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiRangeCorso.setInt(1, corso.getKey());
            sEventiRangeCorso.setDate(2, new java.sql.Date(periodoStart.getTime()));
            sEventiRangeCorso.setDate(3, new java.sql.Date(periodoEnd.getTime()));
            try (ResultSet rs = sEventiRangeCorso.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by range and corso", ex);
        }
        return result;
    }

    @Override
    public void storeEvento(Evento evento) throws DataException {
        try {
            if (evento.getKey() != null && evento.getKey() > 0) {
                if (evento instanceof DataItemProxy && !((DataItemProxy) evento).isModified()) {
                    return;
                }
                // UPDATE
                uEvento.setString(1, evento.getNome());
                uEvento.setDate(2, evento.getGiorno());
                uEvento.setTime(3, evento.getOrarioInizio());
                uEvento.setTime(4, evento.getOrarioFine());
                uEvento.setString(5, evento.getDescrizione());
                uEvento.setString(6, evento.getTipoEvento().toString());
                if (evento.getResponsabile() != null) {
                    uEvento.setInt(7, (int) evento.getResponsabile().getKey());
                } else {
                    uEvento.setNull(7, java.sql.Types.INTEGER);
                }
                if (evento.getRicorrenza() != null) {
                    uEvento.setInt(8, (int) evento.getRicorrenza().getKey());
                } else {
                    uEvento.setNull(8, java.sql.Types.INTEGER);
                }
                if (evento.getAula() != null) {
                    uEvento.setInt(9, (int) evento.getAula().getKey());
                } else {
                    uEvento.setNull(9, java.sql.Types.INTEGER);
                }
                if (evento.getCorso() != null) {
                    uEvento.setInt(10, (int) evento.getCorso().getKey());
                } else {
                    uEvento.setNull(10, java.sql.Types.INTEGER);
                }

                long current_version = evento.getVersion();
                long next_version = current_version + 1;

                uEvento.setLong(11, next_version);
                // WHERE ID = ? AND VERSION = ?
                uEvento.setInt(12, evento.getKey());
                uEvento.setLong(13, current_version);

                if (uEvento.executeUpdate() == 0) {
                    throw new OptimisticLockException(evento);
                } else {
                    evento.setVersion(next_version);
                }
            } else { //INSERT
                iEvento.setString(1, evento.getNome());
                iEvento.setDate(2, evento.getGiorno());
                iEvento.setTime(3, evento.getOrarioInizio());
                iEvento.setTime(4, evento.getOrarioFine());
                iEvento.setString(5, evento.getDescrizione());
                iEvento.setString(6, evento.getTipoEvento().toString());
                if (evento.getResponsabile() != null) {
                    iEvento.setInt(7, (int) evento.getResponsabile().getKey());
                } else {
                    iEvento.setNull(7, java.sql.Types.INTEGER);
                }
                if (evento.getRicorrenza() != null) {
                    iEvento.setInt(8, (int) evento.getRicorrenza().getKey());
                } else {
                    iEvento.setNull(8, java.sql.Types.INTEGER);
                }
                if (evento.getAula() != null) {
                    iEvento.setInt(9, (int) evento.getAula().getKey());
                } else {
                    iEvento.setNull(9, java.sql.Types.INTEGER);
                }
                if (evento.getCorso() != null) {
                    iEvento.setInt(10, (int) evento.getCorso().getKey());
                } else {
                    iEvento.setNull(10, java.sql.Types.INTEGER);
                }

                if (iEvento.executeUpdate() == 1) {
                    try (ResultSet keys = iEvento.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            evento.setKey(key);
                            dataLayer.getCache().add(Evento.class, evento);
                        }
                    }
                }
            }
            // RESET dell'attributo modified
            if (evento instanceof DataItemProxy) {
                ((DataItemProxy) evento).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store evento", ex);
        }
    }

    @Override
    public void assignRicorrenza(Evento evento, Ricorrenza ricorrenza) throws DataException {
        evento.setRicorrenza(ricorrenza);
        storeEvento(evento);
    }

    @Override
    public void assignResponsabile(Evento evento, Responsabile responsabile) throws DataException {
        evento.setResponsabile(responsabile);
        storeEvento(evento);
    }

    @Override
    public void assignAula(Evento evento, Aula aula) throws DataException {
        evento.setAula(aula);
        storeEvento(evento);
    }

    @Override
    public void assignCorso(Evento evento, Corso corso) throws DataException {
        evento.setCorso(corso);
        storeEvento(evento);
    }

    @Override
    public void updateEventiRicorrenti(Evento evento, Ricorrenza ricorrenza) throws DataException {
        try {
            if (evento.getKey() != null && evento.getKey() > 0) {
                if (evento instanceof DataItemProxy && !((DataItemProxy) evento).isModified()) {
                    return;
                }
                // UPDATE
                uEventiRicorrenti.setString(1, evento.getNome());
                uEventiRicorrenti.setTime(2, evento.getOrarioInizio());
                uEventiRicorrenti.setTime(3, evento.getOrarioFine());
                uEventiRicorrenti.setString(4, evento.getDescrizione());
                uEventiRicorrenti.setString(5, evento.getTipoEvento().toString());
                if (evento.getResponsabile() != null) {
                    uEventiRicorrenti.setInt(6, (int) evento.getResponsabile().getKey());
                } else {
                    uEventiRicorrenti.setNull(6, java.sql.Types.INTEGER);
                }
                /*if (evento.getRicorrenza() != null) {
                    uEvento.setInt(8, (int) evento.getRicorrenza().getKey());
                } else {
                    uEvento.setNull(8, java.sql.Types.INTEGER);
                }*/
                if (evento.getAula() != null) {
                    uEventiRicorrenti.setInt(7, (int) evento.getAula().getKey());
                } else {
                    uEventiRicorrenti.setNull(7, java.sql.Types.INTEGER);
                }
                if (evento.getCorso() != null) {
                    uEventiRicorrenti.setInt(8, (int) evento.getCorso().getKey());
                } else {
                    uEventiRicorrenti.setNull(8, java.sql.Types.INTEGER);
                }
                if (ricorrenza != null) {
                    uEventiRicorrenti.setInt(9, (int) ricorrenza.getKey());
                } else {
                    uEventiRicorrenti.setNull(9, java.sql.Types.INTEGER);
                }

                long current_version = evento.getVersion();
                long next_version = current_version + 1;

                uEventiRicorrenti.setLong(10, next_version);
                // WHERE ID_MASTER = ? AND VERSION = ? AND giorno = ?
                uEventiRicorrenti.setInt(11, evento.getRicorrenza().getKey());
                //uEventiRicorrenti.setLong(12, current_version);
                uEventiRicorrenti.setDate(12, evento.getGiorno());

                if (uEventiRicorrenti.executeUpdate() == 0) {
                    throw new OptimisticLockException(evento);
                } else {
                    evento.setVersion(next_version);
                }
            }
            // RESET dell'attributo modified
            if (evento instanceof DataItemProxy) {
                ((DataItemProxy) evento).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to update eventi ricorrenti", ex);
        }
    }

    @Override
    public void deleteEventiRicorrenti(Evento evento) throws DataException {
        // DELETE FROM evento WHERE id_master=? AND giorno > ?
        try {
            dEventiRicorrenti.setInt(1, evento.getRicorrenza().getKey());
            dEventiRicorrenti.setDate(2, evento.getGiorno());
            dEventiRicorrenti.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete eventi ricorrenti", ex);
        }
    }

    @Override
    public List<Evento> createEventiRicorrenti(Evento evento, Ricorrenza ricorrenza) throws DataException {
        ArrayList<Evento> eventiRicorrenti = new ArrayList();
        LocalDate giornoCounter = evento.getGiorno().toLocalDate();
        while (giornoCounter.isBefore(ricorrenza.getDataTermine().toLocalDate())) {
            switch (ricorrenza.getTipoRicorrenza()) {
                case giornaliera -> {
                    giornoCounter = giornoCounter.plusDays(1);
                }
                case settimanale -> {
                    giornoCounter = giornoCounter.plusWeeks(1);
                }
                case mensile -> {
                    giornoCounter = giornoCounter.plusMonths(1);
                }
            }
            Evento e = new EventoImpl(evento.getNome(),
                    java.sql.Date.valueOf(giornoCounter),
                    evento.getOrarioInizio(), evento.getOrarioFine(), evento.getDescrizione(),
                    evento.getTipoEvento(),
                    evento.getResponsabile(),
                    ricorrenza,
                    evento.getAula(),
                    evento.getCorso());
            eventiRicorrenti.add(e);
            storeEvento(e);
        }
        return eventiRicorrenti;
    }

    @Override
    public List<Evento> getEventiNonInseriti(List<Evento> eventi) throws DataException {
        List<Evento> result = new ArrayList<>();
        try {
            // Esegui la query per ottenere tutti gli eventi
            try (ResultSet rs = getEventiNonInseriti.executeQuery()) {
                while (rs.next()) {
                    int eventoId = rs.getInt("id");
                    Evento evento = (Evento) getEvento(eventoId);
                    // Aggiungi l'evento alla lista dei risultati solo se è presente nella lista degli eventi
                    if (isEventoInList(evento, eventi)) {
                        result.add(evento);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by ricorrenza", ex);
        }
        return result;
    }

    // Metodo di utilità per verificare se un evento è nella lista
    private boolean isEventoInList(Evento evento, List<Evento> eventi) {
        for (Evento e : eventi) {
            if (e.getKey() == evento.getKey()) {
                return true;
            }
        }
        return false;
    }

    // Verifica gli eventi in eccesso/non più utili
    private void checkEventi() throws DataException {
        try {
            checkEventi.executeUpdate();
            checkEventi2.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("Error initializing evento data layer", ex);
        }
    }

    @Override
    public Evento getEventiSovrapposti(Evento evento, Aula aula, Date giorno, Time orarioInizio, Time orarioFine) throws DataException {
        Evento e = null;
        try {
            sEventiSovrapposti.setInt(1, evento.getKey());
            sEventiSovrapposti.setInt(2,aula.getKey());
            sEventiSovrapposti.setDate(3, new java.sql.Date(giorno.getTime()));
            sEventiSovrapposti.setTime(4, orarioFine);
            sEventiSovrapposti.setTime(5,orarioInizio);
            try (ResultSet rs = sEventiSovrapposti.executeQuery()) {
                if (rs.next()) {
                    e = createEvento(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi sovrapposti", ex);

        }

        return e;
    }

    @Override
    public List<Evento> getEventiRange(Date periodoStart, Date periodoEnd) throws DataException {
        List<Evento> result = new ArrayList();
        try {
            sEventiRange.setDate(1, new java.sql.Date(periodoStart.getTime()));
            sEventiRange.setDate(2, new java.sql.Date(periodoEnd.getTime()));
            try (ResultSet rs = sEventiRange.executeQuery()) {
                while (rs.next()) {
                    result.add((Evento) getEvento(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load eventi by range and corso", ex);
        }
        return result;
    }
}
