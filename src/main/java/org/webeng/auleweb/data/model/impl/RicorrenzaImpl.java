/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import org.webeng.auleweb.framework.data.DataItemImpl;
import java.sql.Date;
import java.util.List;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoRicorrenza;

/**
 *
 * @author enric
 */
public class RicorrenzaImpl extends DataItemImpl<Integer> implements Ricorrenza {

    private TipoRicorrenza tipoRicorrenza;
    private Date dataTermine;
    private List<Evento> eventi;
    

    public RicorrenzaImpl() {
        super();
        this.tipoRicorrenza = null;
        this.dataTermine = null;
    }

    public RicorrenzaImpl(TipoRicorrenza tipoRicorrenza, Date dataTermine) {
        this.tipoRicorrenza = tipoRicorrenza;
        this.dataTermine = dataTermine;
    }

    @Override
    public TipoRicorrenza getTipoRicorrenza() {
        return this.tipoRicorrenza;
    }

    @Override
    public void setTipoRicorrenza(TipoRicorrenza tipoRicorrenza) {
        this.tipoRicorrenza = tipoRicorrenza;
    }

    @Override
    public Date getDataTermine() {
        return this.dataTermine;
    }

    @Override
    public void setDataTermine(Date dataTermine) {
        this.dataTermine = dataTermine;
    }

    @Override
    public List<Evento> getEventi() {
        return this.eventi;
    }

    @Override
    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }
}
