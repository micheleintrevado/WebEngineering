package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import java.util.List;
import org.webeng.auleweb.data.model.Attrezzatura;

import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author enric
 */
public class AulaImpl extends DataItemImpl<Integer> implements Aula {

    public String nome;
    public String luogo;
    public String edificio;
    public String piano;
    public int capienza;
    public Responsabile responsabile;
    public int preseElettriche;
    public int preseRete;
    public String note;
    public List<Attrezzatura> attrezzature;
    public List<Gruppo> gruppi;
    
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getLuogo() {
        return luogo;
    }

    @Override
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    @Override
    public String getEdificio() {
        return edificio;
    }

    @Override
    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    @Override
    public String getPiano() {
        return piano;
    }

    @Override
    public void setPiano(String piano) {
        this.piano = piano;
    }

    @Override
    public int getCapienza() {
        return capienza;
    }

    @Override
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    @Override
    public Responsabile getResponsabile() {
        return responsabile;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @Override
    public int getPreseElettriche() {
        return preseElettriche;
    }

    @Override
    public void setPreseElettriche(int preseElettriche) {
        this.preseElettriche = preseElettriche;
    }

    @Override
    public int getPreseRete() {
        return preseRete;
    }

    @Override
    public void setPreseRete(int preseRete) {
        this.preseRete = preseRete;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public List<Attrezzatura> getAttrezzature() {
        return attrezzature;
    }

    @Override
    public void setAttrezzature(List<Attrezzatura> attrezzature) {
        this.attrezzature = attrezzature;
    }

    @Override
    public List<Gruppo> getGruppi() {
        return gruppi;
    }

    @Override
    public void setGruppi(List<Gruppo> gruppi) {
        this.gruppi = gruppi;
    }
    
}
