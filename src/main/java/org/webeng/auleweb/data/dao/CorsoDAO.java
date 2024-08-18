/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import org.webeng.auleweb.framework.data.DataException;
import java.util.List;
import org.webeng.auleweb.data.model.Corso;

/**
 *
 * @author enric
 */
public interface CorsoDAO {
    Corso createCorso();

    Corso getCorso(int corso_key) throws DataException;

    // Lista di tutti i corsi
    List<Corso> getCorsi() throws DataException;
    
    // Inserimento e aggiornamento corso in DB
    void storeCorso(Corso corso) throws DataException;
    
    void deleteCorso(Corso c) throws DataException;

    public List<Corso> getCorsiBySearch(String keyword) throws DataException;
}
