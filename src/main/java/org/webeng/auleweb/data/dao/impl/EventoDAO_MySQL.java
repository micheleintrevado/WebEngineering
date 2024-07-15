/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.util.Date;
import java.util.List;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.framework.data.DAO;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author enric
 */
public class EventoDAO_MySQL extends DAO implements EventoDAO {

    private PreparedStatement sEventoByID, sEventoByAula, sEventiByResponsabile, sEventiByCorso,
            sEventiByRicorrenza, sEventiByGiorno, sEventiByAulaAndGiorno,
            sEventiBySettimanaAula, sEventiBySettimanaCorso,
            sEventiAttuali, sEventiProssimi;

    private PreparedStatement sEventiRange;
    private PreparedStatement sEventi;

    private PreparedStatement iEvento, uEvento, dEvento;

    public EventoDAO_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {
        try {
            super.init();
            sEventoByID = connection.prepareStatement("SELECT * FROM evento WHERE ID=?");
            sEventoByAula = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula=?");
            sEventiByResponsabile = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_responsabile = ?");
            sEventiByCorso = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ?");
            sEventiByRicorrenza = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_master = ?");
            sEventiByGiorno = connection.prepareStatement("SELECT id FROM evento WHERE evento.giorno = ?"); // GIORNO = '2024-05-14'
            sEventiByAulaAndGiorno = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula = ? AND evento.giorno = ?"); // GIORNO = '2024-05-14'

            sEventiBySettimanaAula = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_aula = ? and evento.giorno BETWEEN ? AND date_add(?, interval 1 week)");
            sEventiBySettimanaCorso = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ? and evento.giorno BETWEEN ? AND date_add(?, interval 1 week)");
            sEventiAttuali = connection.prepareStatement("SELECT id FROM evento WHERE giorno = date(now()) AND time(now()) BETWEEN orario_inizio AND orario_fine");
            sEventiProssimi = connection.prepareStatement("SELECT id FROM evento WHERE TIMESTAMP(giorno, orario_inizio) BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? HOUR)"); // Default 3 ore

            sEventiRange = connection.prepareStatement("SELECT id FROM evento WHERE evento.id_corso = ? AND giorno between ? AND ? ");

            sEventi = connection.prepareStatement("SELECT id FROM evento");

            iEvento = connection.prepareStatement("INSERT INTO evento(nome, giorno, orario_inizio, orario_fine, descrizione, tipologia, id_responsabile, id_master, id_aula, id_corso) VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uEvento = connection.prepareStatement("UPDATE evento set nome=?, giorno=?, orario_inizio=?,orario_fine=?,descrizione=?,tipologia=?,prese_rete=?,id_responsabile=?,id_master=?,id_aula=?,id_corso=?,version=? WHERE id=? and version=?");
            dEvento = connection.prepareStatement("DELETE FROM evento WHERE id=?");
        } catch (SQLException ex) {
            throw new DataException("Error initializing evento data layer", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            sEventoByID.close();
            sEventoByAula.close();
            sEventiByResponsabile.close();
            sEventiByCorso.close();
            sEventiByRicorrenza.close();
            sEventiByGiorno.close();
            sEventiByAulaAndGiorno.close();
            sEventiBySettimanaAula.close();
            sEventiBySettimanaCorso.close();
            sEventiAttuali.close();
            sEventiProssimi.close();

            sEventiRange.close();
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Evento getEvento(int evento_key) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventi() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiByAula(Aula aula) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiByResponsabile(Responsabile responsabile) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiByCorso(Corso corso) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiGiorno(Date giorno) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiAulaGiorno(Aula aula, Date giorno) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiSettimana(Aula aula, Date settimana) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiSettimana(Corso corso, Date settimana) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiAttuali() throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiProssimi(int prossime_ore) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Evento> getEventiRicorrenti(Ricorrenza ricorrenza) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getEventiRangeCSV(Date periodoStart, Date periodoEnd, Corso corso) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getEventiRangeiCalendar(Date periodoStart, Date periodoEnd, Corso corso) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void storeEvento(Evento evento) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void assignRicorrenza(Evento evento, Ricorrenza ricorrenza) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
