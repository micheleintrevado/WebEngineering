/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import org.webeng.auleweb.framework.data.DataException;
import java.util.List;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;

/**
 *
 * @author enric
 */
public interface AttrezzaturaDAO {

    Attrezzatura createAttrezzatura();

    Attrezzatura getAttrezzatura(int attrezzatura_key) throws DataException;

    // Lista di tutte le attrezzature
    List<Attrezzatura> getAttrezzatura() throws DataException;

    // Attrezzature di un'aula
    List<Attrezzatura> getAttrezzaturaByAula(Aula aula) throws DataException;

    // Lista di aule senza eventi in un determinato giorno
    // List<Aula> getUnassignedAule(Date) throws DataException;   
    // Inserimento e aggiornamento aula in DB
    void storeAttrezzatura(Attrezzatura attrezzatura) throws DataException;

    // TODO
    // Assegna (aggiunge)? una attrezzatura a una specifica aula 
    void assignAttrezzatura(Attrezzatura attrezzatura, Aula aula) throws DataException;
    
    void deleteAttrezzatura(Attrezzatura at) throws DataException;
    void deleteAttrezzatureAula(Aula a) throws DataException;

}
