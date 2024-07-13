/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import org.webeng.auleweb.framework.data.DataItem;

/**
 *
 * @author enric
 */
public interface Responsabile extends DataItem<Integer>{
    String getNome();
    void setNome(String nome);
    
    String getEmail();
    void setEmail(String email);
}
