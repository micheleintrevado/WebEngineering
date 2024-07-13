/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import org.webeng.auleweb.framework.data.DataItem;
import java.util.List;

/**
 *
 * @author enric
 */
public interface Gruppo extends DataItem<Integer>{
    String getNome();
    void setNome(String nome);
    
    String getDescrizione();
    void setDescrizione(String descrizione);
    
    List<Aula> getAule();
    void setAule(List<Aula> aule);
    
    void addAula(Aula a);
}
