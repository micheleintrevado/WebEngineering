/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import it.univaq.f4i.iw.framework.data.DataException;
import java.util.List;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author enric
 */
public interface AulaDAO {

    Aula createAula();

    Aula getAula(int aula_key) throws DataException;

    // Lista di tutte le aule
    List<Aula> getAule() throws DataException;

    // Aula dato un evento 
    Aula getAulaByEvento(Evento evento) throws DataException;

    // Lista di aule dato un responsabile 
    List<Aula> getAuleByResponsabile(Responsabile responsabile) throws DataException;

    // Lista di aule dato un gruppo
    List<Aula> getAuleByGruppo(Gruppo gruppo) throws DataException;

    // Lista di aule dato un gruppo
    List<Aula> getAuleSenzaGruppo() throws DataException;

    // Lista di aule senza eventi
    List<Aula> getUnassignedAule() throws DataException;

    // Lista di aule senza eventi in un determinato giorno
    // List<Aula> getUnassignedAule(Date) throws DataException;   
    // Inserimento e aggiornamento aula in DB
    void storeAula(Aula aula) throws DataException;

}
