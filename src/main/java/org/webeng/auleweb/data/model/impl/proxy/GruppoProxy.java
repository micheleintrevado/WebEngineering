/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import it.univaq.f4i.iw.framework.data.DataException;
import it.univaq.f4i.iw.framework.data.DataItemProxy;
import it.univaq.f4i.iw.framework.data.DataLayer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.impl.GruppoImpl;


/**
 *
 * @author miche
 */
public class GruppoProxy extends GruppoImpl implements DataItemProxy{
    
    protected boolean modified;
    
    protected DataLayer dataLayer;
    
    public GruppoProxy(DataLayer dataLayer){
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }
    
    @Override
    public List<Aula> getAule() {
        if (super.getAule()== null) {
            try {
                super.setAule(((AulaDAO) dataLayer.getDAO(Aula.class)).getAuleByGruppo(this));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAule();
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setAule(List<Aula> aule) {
        super.setAule(aule);
        this.modified = true;
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
    
}
