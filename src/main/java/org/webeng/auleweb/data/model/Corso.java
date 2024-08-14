/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import java.util.List;
import org.webeng.auleweb.framework.data.DataItem;

/**
 *
 * @author enric
 */
public interface Corso extends DataItem<Integer>{
    String getNome();
    void setNome(String nome);
    
    List<Evento> getEventi();
    void setEventi(List<Evento> eventi);
    void addEvento(Evento evento);
}
