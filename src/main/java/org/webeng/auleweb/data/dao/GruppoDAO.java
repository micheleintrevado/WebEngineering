/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import it.univaq.f4i.iw.framework.data.DataException;
import java.util.List;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;

/**
 *
 * @author enric
 */
public interface GruppoDAO {
    Gruppo createGruppo();

    Gruppo getGruppo(int gruppo_key) throws DataException;

    List<Gruppo> getGruppiByAula(Aula aula) throws DataException;

    // Lista di gruppi senza aule associate
    List<Gruppo> getUnassignedGruppi() throws DataException;

    void storeGruppo(Gruppo gruppo) throws DataException;
    
    // TODO
    // Assegna un gruppo ad una specifica aula
    void assignGruppo(Gruppo gruppo, Aula aula) throws DataException;
}
