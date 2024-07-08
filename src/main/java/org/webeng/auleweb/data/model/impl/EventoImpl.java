/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import java.sql.Date;
import java.sql.Time;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoEvento;

/**
 *
 * @author enric
 */
public class EventoImpl extends DataItemImpl<Integer> implements Evento {

    public String nome;
    public Date giorno;
    public Time orarioInizio, orarioFine;
    public String descrizione;
    public TipoEvento tipoEvento;
    public Responsabile responsabile;
    public Ricorrenza ricorrenza;
    public Aula aula;
    public Corso corso;

    public EventoImpl() {
        super();
        this.nome = "";
        this.giorno = null;
        this.orarioInizio = null;
        this.orarioFine = null;
        this.descrizione = "";
        this.tipoEvento = null;
        this.responsabile = null;
        this.ricorrenza = null;
        this.aula = null;
        this.corso = null;
    }

    public EventoImpl(String nome, Date giorno, Time orarioInizio, Time orarioFine, String descrizione, TipoEvento tipoEvento, Responsabile responsabile, Ricorrenza ricorrenza, Aula aula, Corso corso) {
        this.nome = nome;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
        this.orarioFine = orarioFine;
        this.descrizione = descrizione;
        this.tipoEvento = tipoEvento;
        this.responsabile = responsabile;
        this.ricorrenza = ricorrenza;
        this.aula = aula;
        this.corso = corso;
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
    public Date getGiorno() {
        return this.giorno;
    }

    @Override
    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    @Override
    public Time getOrarioInizio() {
        return this.orarioInizio;
    }

    @Override
    public void setOrarioInizio(Time orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    @Override
    public Time getOrarioFine() {
        return this.orarioFine;
    }

    @Override
    public void setOrarioFine(Time orarioFine) {
        this.orarioFine = orarioFine;
    }

    @Override
    public String getDescrizione() {
        return this.descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public TipoEvento getTipoEvento() {
        return this.tipoEvento;
    }

    @Override
    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Override
    public Responsabile getResponsabile() {
        return this.responsabile;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public Ricorrenza getRicorrenza() {
        return this.ricorrenza;
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        this.ricorrenza = ricorrenza;
    }

    @Override
    public Aula getAula() {
        return this.aula;
    }

    @Override
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public Corso getCorso() {
        return this.corso;
    }

    @Override
    public void setCorso(Corso corso) {
        this.corso = corso;
    }

}
