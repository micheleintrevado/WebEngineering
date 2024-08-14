/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import java.util.List;
import org.webeng.auleweb.framework.data.DataItemImpl;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;

/**
 *
 * @author miche
 */
public class CorsoImpl extends DataItemImpl<Integer> implements Corso {

    private String nome;
    private List<Evento> eventi;

    public CorsoImpl() {
        super();
        this.nome = "";
        this.eventi = null;
    }

    public CorsoImpl(String nome, List<Evento> eventi) {
        this.nome = nome;
        this.eventi = eventi;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public List<Evento> getEventi() {
        return eventi;
    }

    @Override
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    @Override
    public void addEvento(Evento evento) {
        this.eventi.add(evento);
    }
}
