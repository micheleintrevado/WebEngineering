/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import it.univaq.f4i.iw.framework.data.DataException;
import java.time.Period;
import java.util.Date;
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

    // Eventi associati ad una specifica aula in un determinato giorno
    List<Evento> getEventiGiorno(Aula aula, Date giorno) throws DataException;

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

    //TODO: Object = file CSV
    // Lista di eventi in un determinato periodo in formato CSV
    Object getEventiRangeCSV(Period periodo, Corso corso) throws DataException;
    
    //TODO: Object = file iCalendar
    // Lista di eventi in un determinato periodo in formato iCalendar
    Object getEventiRangeiCalendar(Period periodo, Corso corso) throws DataException;

    // Inserimento e aggiornamento evento in DB
    void storeEvento(Evento evento) throws DataException;
    
    // TODO ??
    // Assegna una ricorrenza ad uno specifico evento
    void assignRicorrenza(Evento evento, Ricorrenza ricorrenza) throws DataException;
}
