/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import org.webeng.auleweb.framework.data.DataException;
import java.util.List;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author enric
 */
public interface ResponsabileDAO {
    
    Responsabile createResponsabile();

    Responsabile getResponsabile(int responsabile_key) throws DataException;

    // Lista di tutti i responsabili
    List<Responsabile> getResponsabili() throws DataException;
    
    // Responsabile dato un evento 
    Responsabile getResponsabileByEvento(Evento evento) throws DataException;

    // Lista di responsabili senza eventi
    List<Responsabile> getUnassignedResponsabili() throws DataException;

    // Inserimento e aggiornamento responsabile in DB
    void storeResponsabile(Responsabile responsabile) throws DataException;    

}
