/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import it.univaq.f4i.iw.framework.data.DataException;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Ricorrenza;

/**
 *
 * @author enric
 */
public interface RicorrenzaDAO {
    Ricorrenza createRicorrenza();

    Ricorrenza getRicorrenza(int ricorrenza_key) throws DataException;

    // Ottieni tipo e data termine per la ricorrenza di uno specifico evento
    Ricorrenza getRicorrenzaByEvento(Evento evento) throws DataException;

    void storeRicorrenza(Ricorrenza ricorrenza) throws DataException;
}
