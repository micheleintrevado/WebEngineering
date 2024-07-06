/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import it.univaq.f4i.iw.framework.data.DataItem;
import java.util.List;

/**
 *
 * @author enric
 */
public interface Attrezzatura extends DataItem<Integer>{
   String getTipo();
   void setTipo(String tipo);
   
   List<Aula> getAule();
    void setAule(List<Aula> aule);
}
