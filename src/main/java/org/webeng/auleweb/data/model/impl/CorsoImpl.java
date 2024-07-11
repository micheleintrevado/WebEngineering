/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import org.webeng.auleweb.data.model.Corso;

/**
 *
 * @author miche
 */
public class CorsoImpl extends  DataItemImpl<Integer> implements Corso{
    
    private String nome;
 
    public CorsoImpl() {
        super();
        this.nome = "";
    }

    public CorsoImpl(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
