/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import java.util.List;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;

/**
 *
 * @author enric
 */
public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo {

    public String nome;
    public String descrizione;
    public List<Aula> aule;
    
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
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
    public List<Aula> getAule() {
        return this.aule;
    }

    @Override
    public void setAule(List<Aula> aule) {
        this.aule = aule;
    }
    
}
