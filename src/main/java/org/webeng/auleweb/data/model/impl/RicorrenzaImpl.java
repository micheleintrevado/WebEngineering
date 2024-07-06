/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import java.sql.Date;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoRicorrenza;

/**
 *
 * @author enric
 */
public class RicorrenzaImpl extends DataItemImpl<Integer> implements Ricorrenza {

    public TipoRicorrenza tipoRicorrenza;
    public Date dataTermine;
    
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
}
