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
import org.webeng.auleweb.data.dao.AttrezzaturaDAO;
import org.webeng.auleweb.data.dao.GruppoDAO;
import org.webeng.auleweb.data.dao.ResponsabileDAO;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Gruppo;
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

    public void setResponsabileKey(int responsabile_key) {
        this.responsabile_key = responsabile_key;
        super.setResponsabile(null);
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        this.responsabile_key = responsabile.getKey();
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setPreseRete(int preseRete) {
        super.setPreseRete(preseRete);
        this.modified = true;
    }

    @Override
    public void setPreseElettriche(int preseElettriche) {
        super.setPreseElettriche(preseElettriche);
        this.modified = true;
    }

    @Override
    public void setPiano(String piano) {
        super.setPiano(piano);
        this.modified = true;
    }

    @Override
    public void setLuogo(String luogo) {
        super.setLuogo(luogo); 
        this.modified = true;
    }

    @Override
    public void setEdificio(String edificio) {
        super.setEdificio(edificio); 
        this.modified = true;
    }

    @Override
    public void setCapienza(int capienza) {
        super.setCapienza(capienza); 
        this.modified = true;
    }

    @Override
    public void setNote(String note) {
        super.setNote(note); 
        this.modified = true;
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

    @Override
    public List<Attrezzatura> getAttrezzature() {
        if (super.getAttrezzature() == null) {
            try {
                super.setAttrezzature(((AttrezzaturaDAO) dataLayer.getDAO(Attrezzatura.class)).getAttrezzaturaByAula(this));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getAttrezzature();
    }

    @Override
    public void setAttrezzature(List<Attrezzatura> attrezzatura) {
        super.setAttrezzature(attrezzatura);
        this.modified = true;
    }

    @Override
    public List<Gruppo> getGruppi() {
        if (super.getGruppi() == null) {
            try {
                super.setGruppi(((GruppoDAO) dataLayer.getDAO(Gruppo.class)).getGruppiByAula(this));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getGruppi();
    }

    @Override
    public void setGruppi(List<Gruppo> gruppi) {
        super.setGruppi(gruppi);
        this.modified = true;
    }

    @Override
    public void addAttrezzatura(Attrezzatura attrezzatura) {
        super.addAttrezzatura(attrezzatura);
        this.modified = true;
    }

    @Override
    public void addGruppo(Gruppo gruppo) {
        super.addGruppo(gruppo);
        this.modified = true;
    }
}
