/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;
import org.webeng.auleweb.data.model.impl.ResponsabileImpl;
import org.webeng.auleweb.framework.data.DataException;

/**
 *
 * @author enric
 */
public class ResponsabileProxy extends ResponsabileImpl implements DataItemProxy {

    protected boolean modified;

    protected DataLayer dataLayer;

    public ResponsabileProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

    @Override
    public List<Aula> getAule() {
        if (super.getAule() == null) {
            try {
                super.setAule(((AulaDAO) dataLayer.getDAO(Aula.class)).getAuleByResponsabile(this));
            } catch (DataException ex) {
                Logger.getLogger(ResponsabileProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAule();
    }
    
    @Override
    public void setAule(List<Aula> aule){
        super.setAule(aule);
        this.modified = true;
    }
    
    @Override
    public void addAula(Aula aula){
        super.addAula(aula);
        this.modified = true;
    }
    
    @Override
    public List<Evento> getEventi() {
        if (super.getEventi() == null) {
            try {
                super.setEventi(((EventoDAO) dataLayer.getDAO(Evento.class)).getEventiByResponsabile(this));
            } catch (DataException ex) {
                Logger.getLogger(ResponsabileProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getEventi();
    }
    
    @Override
    public void setEventi(List<Evento> eventi){
        super.setEventi(eventi);
        this.modified = true;
    }
    
    @Override
    public void addEvento(Evento evento){
        super.addEvento(evento);
        this.modified = true;
    }
    
}
