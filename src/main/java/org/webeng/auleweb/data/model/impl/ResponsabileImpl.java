/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import java.util.List;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataItemImpl;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author enric
 */
public class ResponsabileImpl extends DataItemImpl<Integer> implements Responsabile {

    private String nome;
    private String email;
    private List<Aula> aule;
    private List<Evento> eventi;

    public ResponsabileImpl() {
        super();
        this.nome = "";
        this.email = "";
        this.aule = null;
        this.eventi = null;
    }

    public ResponsabileImpl(String nome, String email, List<Aula> aule, List<Evento> eventi) {
        this.nome = nome;
        this.email = email;
        this.aule = aule;
        this.eventi = eventi;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public List<Aula> getAule() {
        return aule;
    }

    @Override
    public void setAule(List<Aula> aula) {
        this.aule = aula;
    }

    @Override
    public void addAula(Aula aula) {
        this.aule.add(aula);
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
