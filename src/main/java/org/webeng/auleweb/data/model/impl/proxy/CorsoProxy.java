/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.impl.CorsoImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class CorsoProxy extends CorsoImpl implements DataItemProxy{
    protected boolean modified;
    
    protected DataLayer dataLayer;
    
    public CorsoProxy(DataLayer dataLayer){
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }
    
    @Override
    public void setNome(String n){
        super.setNome(n);
        this.modified = true;
    }
    
    @Override
    public void setKey(Integer key){
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
    public List<Evento> getEventi() {
        if (super.getEventi() == null) {
            try {
                super.setEventi(((EventoDAO) dataLayer.getDAO(Evento.class)).getEventiByCorso(this));
            } catch (DataException ex) {
                Logger.getLogger(CorsoProxy.class.getName()).log(Level.SEVERE, null, ex);
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
