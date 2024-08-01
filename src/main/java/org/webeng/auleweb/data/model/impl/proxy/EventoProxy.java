/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.dao.CorsoDAO;
import org.webeng.auleweb.data.dao.ResponsabileDAO;
import org.webeng.auleweb.data.dao.RicorrenzaDAO;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.data.model.TipoEvento;
import org.webeng.auleweb.data.model.impl.EventoImpl;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author enric
 */
public class EventoProxy extends EventoImpl implements DataItemProxy {

    protected boolean modified;

    protected DataLayer dataLayer;

    protected int responsabile_key;
    protected int ricorrenze_key;
    protected int aula_key;
    protected int corso_key;

    public EventoProxy(DataLayer dataLayer) {
        super();
        this.modified = false;
        this.dataLayer = dataLayer;
        this.responsabile_key = 0;
        this.ricorrenze_key = 0;
        this.aula_key = 0;
        this.corso_key = 0;
    }

    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }

    public void setResponsabileKey(int responsabile_key) {
        this.responsabile_key = responsabile_key;
        super.setResponsabile(null);

    }

    public void setRicorrenzeKey(int ricorrenze_key) {
        this.ricorrenze_key = ricorrenze_key;
        super.setRicorrenza(null);
    }

    public void setAulaKey(int aula_key) {
        this.aula_key = aula_key;
        super.setAula(null);
    }

    public void setCorsoKey(int corso_key) {
        this.corso_key = corso_key;
        super.setCorso(null);
    }

    @Override
    public void setRicorrenza(Ricorrenza ricorrenza) {
        super.setRicorrenza(ricorrenza);
        if (ricorrenza != null) {
            this.ricorrenze_key = ricorrenza.getKey();
        } else {
            this.ricorrenze_key = 0;
        }
        this.modified = true;
    }

    @Override
    public void setCorso(Corso corso) {
        super.setCorso(corso);
        this.corso_key = corso.getKey();
        this.modified = true;
    }

    @Override
    public void setAula(Aula aula) {
        super.setAula(aula);
        this.aula_key = aula.getKey();
        this.modified = true;
    }

    @Override
    public void setTipoEvento(TipoEvento tipoEvento) {
        super.setTipoEvento(tipoEvento);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setResponsabile(Responsabile responsabile) {
        super.setResponsabile(responsabile);
        this.responsabile_key = responsabile.getKey();
        this.modified = true;
    }

    @Override
    public void setOrarioFine(Time orarioFine) {
        super.setOrarioFine(orarioFine);
        this.modified = true;
    }

    @Override
    public void setOrarioInizio(Time orarioInizio) {
        super.setOrarioInizio(orarioInizio);
        this.modified = true;
    }

    @Override
    public void setGiorno(Date giorno) {
        super.setGiorno(giorno);
        this.modified = true;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
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
    public Ricorrenza getRicorrenza() {
        if (super.getRicorrenza() == null && ricorrenze_key > 0) {
            try {
                super.setRicorrenza(((RicorrenzaDAO) dataLayer.getDAO(Ricorrenza.class)).getRicorrenza(ricorrenze_key));
            } catch (DataException ex) {
                Logger.getLogger(RicorrenzaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getRicorrenza();
    }

    @Override
    public Aula getAula() {
        if (super.getAula() == null && aula_key > 0) {
            try {
                super.setAula(((AulaDAO) dataLayer.getDAO(Aula.class)).getAula(aula_key));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getAula();
    }

    @Override
    public Corso getCorso() {
        if (super.getCorso() == null && corso_key > 0) {
            try {
                super.setCorso(((CorsoDAO) dataLayer.getDAO(Corso.class)).getCorso(corso_key));
            } catch (DataException ex) {
                Logger.getLogger(AulaProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return super.getCorso();
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

}
