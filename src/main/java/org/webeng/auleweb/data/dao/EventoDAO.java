/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import java.sql.Time;
import org.webeng.auleweb.framework.data.DataException;
import java.time.Period;
import java.sql.Date;
import java.util.List;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;

/**
 *
 * @author enric
 */
public interface EventoDAO {

    Evento createEvento();

    Evento getEvento(int evento_key) throws DataException;

    // Lista di tutti gli eventi
    List<Evento> getEventi() throws DataException;

    // Eventi associati ad una specifica aula/responsabile/corso
    List<Evento> getEventiByAula(Aula aula) throws DataException;

    List<Evento> getEventiByResponsabile(Responsabile responsabile) throws DataException;

    List<Evento> getEventiByCorso(Corso corso) throws DataException;

    // Eventi in un determinato giorno
    List<Evento> getEventiGiorno(Date giorno) throws DataException;

    // Eventi associati ad una specifica aula in un determinato giorno
    List<Evento> getEventiAulaGiorno(Aula aula, Date giorno) throws DataException;

    // Evento associati ad una specifica aula in un determinato giorno e orario
    Evento getEventiSovrapposti(Evento evento, Aula aula, Date giorno, Time OrarioInizio, Time OrarioFine) throws DataException;

    // Evento già presente in una specifica aula in un determinato giorno e orario
    Evento getEventoByAulaGiornoOrario(Aula aula, Date giorno, Time orarioInizio, Time orarioFine) throws DataException;

    // Eventi associati ad una specifica aula nella settimana che parte dal giorno indicato
    List<Evento> getEventiSettimana(Aula aula, Date settimana) throws DataException;

    // Eventi associati ad uno specifico corso in una determinata settimana
    List<Evento> getEventiSettimana(Corso corso, Date settimana) throws DataException;

    // Eventi attuali e prossime 
    List<Evento> getEventiAttuali() throws DataException;

    // Eventi prossimi (default 3 ore)
    List<Evento> getEventiProssimi(int prossime_ore) throws DataException;

    // Eventi data una ricorrenza
    List<Evento> getEventiRicorrenti(Ricorrenza ricorrenza) throws DataException;

    // Lista di eventi per un corso in un determinato periodo in formato CSV/iCalendar
    List<Evento> getEventiRangeCorso(Date periodoStart, Date periodoEnd, Corso corso) throws DataException;

    // Lista di eventi in un determinato periodo in formato CSV/iCalendar
    List<Evento> getEventiRange(Date periodoStart, Date periodoEnd) throws DataException;

    // Lista di eventi non inseriti durante le operazioni di ricorrenza evento
    List<Evento> getEventiNonInseriti(List<Evento> eventi) throws DataException;

    // Inserimento e aggiornamento evento in DB
    void storeEvento(Evento evento) throws DataException;

    // Aggiorna tutti gli eventi con stesso id_master, assegnando i valori dell'evento corrente
    void updateEventiRicorrenti(Evento evento, Ricorrenza ricorrenza) throws DataException;

    // Assegna una ricorrenza/responsabile/aula/corso ad uno specifico evento
    void assignRicorrenza(Evento evento, Ricorrenza ricorrenza) throws DataException;

    void assignResponsabile(Evento evento, Responsabile responsabile) throws DataException;

    void assignAula(Evento evento, Aula aula) throws DataException;

    void assignCorso(Evento evento, Corso corso) throws DataException;

    List<Evento> createEventiRicorrenti(Evento evento, Ricorrenza ricorrenza) throws DataException;

    // Elimino tutti gli eventi ricorrenti a partire da quello indicato
    void deleteEventiRicorrenti(Evento evento) throws DataException;

}
