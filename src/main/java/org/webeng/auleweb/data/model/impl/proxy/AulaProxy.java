/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import it.univaq.f4i.iw.framework.data.DataException;
import it.univaq.f4i.iw.framework.data.DataItemProxy;
import it.univaq.f4i.iw.framework.data.DataLayer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.ResponsabileDAO;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.impl.AulaImpl;

/**
 *
 * @author enric
 */
public class AulaProxy extends AulaImpl implements DataItemProxy {

    protected boolean modified;

    protected DataLayer dataLayer;

    protected int responsabile_key;

    public AulaProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
        this.responsabile_key = 0;
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

    public void setResponsabile_key(int responsabile_key) {
        this.responsabile_key = responsabile_key;
        super.setResponsabile(null);
    }

    @Override
    public Responsabile getResponsabile() {
        if (super.getResponsabile() == null && responsabile_key > 0) {
            try {
                super.setResponsabile(((ResponsabileDAO) dataLayer.getDAO(Responsabile.class)).getResponsabile(responsabile_key));

            } catch (DataException ex) {
                Logger.getLogger(ResponsabileProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getResponsabile();

    }
}
