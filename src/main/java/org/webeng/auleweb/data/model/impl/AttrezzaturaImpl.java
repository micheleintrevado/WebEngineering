/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import java.util.List;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;

/**
 *
 * @author miche
 */
public class AttrezzaturaImpl extends DataItemImpl<Integer> implements Attrezzatura{
    
    public String tipo;
    public List<Aula> aule;

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public List<Aula> getAule() {
        return aule;
    }

    @Override
    public void setAule(List<Aula> aule) {
        this.aule = aule;
    }
    
}
