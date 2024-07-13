/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.TipoRicorrenza;
import org.webeng.auleweb.data.model.impl.RicorrenzaImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class RicorrenzaProxy extends RicorrenzaImpl implements DataItemProxy{

    protected boolean modified;
    
    protected DataLayer dataLayer;
    
    public RicorrenzaProxy(DataLayer dataLayer){
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }
    
    @Override
    public List<Evento> getEventi() {
        if (super.getEventi()== null) {
            try {
                super.setEventi(((EventoDAO) dataLayer.getDAO(Evento.class)).getEventiRicorrenti(this));
            } catch (DataException ex) {
                Logger.getLogger(RicorrenzaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getEventi();
    }
 
    @Override
    public void setTipoRicorrenza(TipoRicorrenza tipoRicorrenza) {
        super.setTipoRicorrenza(tipoRicorrenza);
        this.modified = true;
    }
    
    @Override
    public void setDataTermine(Date dataTermine) {
        super.setDataTermine(dataTermine);
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
    
}
