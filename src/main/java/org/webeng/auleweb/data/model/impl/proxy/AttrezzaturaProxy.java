/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.impl.AttrezzaturaImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class AttrezzaturaProxy extends AttrezzaturaImpl implements DataItemProxy{
    
    protected boolean modified;

    protected DataLayer dataLayer;
    
    public AttrezzaturaProxy(DataLayer dataLayer){
        super();
        this.dataLayer = dataLayer;
    }
    
    @Override
    public List<Aula> getAule() {
        if (super.getAule()== null) {
            try {
                super.setAule(((AulaDAO) dataLayer.getDAO(Aula.class)).getAuleByAttrezzatura(this));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAule();
    }

    @Override
    public void setTipo(String t){
        super.setTipo(t);
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
