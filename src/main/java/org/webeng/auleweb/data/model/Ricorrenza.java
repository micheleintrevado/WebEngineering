/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model;

import it.univaq.f4i.iw.framework.data.DataItem;
import java.sql.Date;

/**
 *
 * @author enric
 */
public interface Ricorrenza extends DataItem<Integer> {
    TipoRicorrenza getTipoRicorrenza();
    void setTipoRicorrenza(TipoRicorrenza tipoRicorrenza);
    
    Date getDataTermine();
    void setDataTermine(Date dataTermine);
    
}
