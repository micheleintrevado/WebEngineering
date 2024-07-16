/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.application;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.webeng.auleweb.data.dao.AdminDAO;
import org.webeng.auleweb.data.dao.AttrezzaturaDAO;
import org.webeng.auleweb.data.dao.AulaDAO;
import org.webeng.auleweb.data.dao.CorsoDAO;
import org.webeng.auleweb.data.dao.EventoDAO;
import org.webeng.auleweb.data.dao.GruppoDAO;
import org.webeng.auleweb.data.dao.ResponsabileDAO;
import org.webeng.auleweb.data.dao.RicorrenzaDAO;
import org.webeng.auleweb.data.dao.impl.AdminDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.AttrezzaturaDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.AulaDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.CorsoDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.EventoDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.GruppoDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.ResponsabileDAO_MySQL;
import org.webeng.auleweb.data.dao.impl.RicorrenzaDAO_MySQL;
import org.webeng.auleweb.data.model.Admin;
import org.webeng.auleweb.data.model.Attrezzatura;
import org.webeng.auleweb.data.model.Aula;
import org.webeng.auleweb.data.model.Corso;
import org.webeng.auleweb.data.model.Evento;
import org.webeng.auleweb.data.model.Gruppo;
import org.webeng.auleweb.data.model.Responsabile;
import org.webeng.auleweb.data.model.Ricorrenza;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author enric
 */
public class AulewebDataLayer extends DataLayer {

    public AulewebDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }

    @Override
    public void init() throws DataException {
        registerDAO(Admin.class, new AdminDAO_MySQL(this));
        registerDAO(Attrezzatura.class, new AttrezzaturaDAO_MySQL(this));
        registerDAO(Aula.class, new AulaDAO_MySQL(this));
        registerDAO(Corso.class, new CorsoDAO_MySQL(this));
        registerDAO(Evento.class, new EventoDAO_MySQL(this));
        registerDAO(Gruppo.class, new GruppoDAO_MySQL(this));
        registerDAO(Responsabile.class, new ResponsabileDAO_MySQL(this));
        registerDAO(Ricorrenza.class, new RicorrenzaDAO_MySQL(this));
    }

    public AdminDAO getAdminDAO() {
        return (AdminDAO) getDAO(Admin.class);
    }

    public AttrezzaturaDAO getAttrezzaturaDAO() {
        return (AttrezzaturaDAO) getDAO(Attrezzatura.class);
    }

    public AulaDAO getAulaDAO() {
        return (AulaDAO) getDAO(Aula.class);
    }

    public CorsoDAO getCorsoDAO() {
        return (CorsoDAO) getDAO(Corso.class);
    }

    public EventoDAO getEventoDAO() {
        return (EventoDAO) getDAO(Evento.class);
    }

    public GruppoDAO getGruppoDAO() {
        return (GruppoDAO) getDAO(Gruppo.class);
    }

    public ResponsabileDAO getResponsabileDAO() {
        return (ResponsabileDAO) getDAO(Responsabile.class);
    }

    public RicorrenzaDAO getRicorrenzaDAO() {
        return (RicorrenzaDAO) getDAO(Ricorrenza.class);
    }
}
