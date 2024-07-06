/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import it.univaq.f4i.iw.framework.data.DataItem;
import java.sql.Date;
import java.sql.Time;

import java.time.LocalTime;

/**
 *
 * @author enric
 */
public interface Evento extends DataItem<Integer> {

    String getNome();

    void setNome(String nome);

    Date getGiorno();

    void setGiorno(Date giorno);

    Time getOrarioInizio();

    void setOrarioInizio(Time orarioInizio);

    Time getOrarioFine();

    void setOrarioFine(Time orarioFine);

    String getDescrizione();

    void setDescrizione(String descrizione);

    TipoEvento getTipoEvento();

    void setTipoEvento(TipoEvento tipoEvento);

    Responsabile getResponsabile();

    void setResponsabile(Responsabile responsabile);

    Ricorrenza getRicorrenza();

    void setRicorrenza(Ricorrenza ricorrenza);

    Aula getAula();

    void setAula(Aula aula);

    Corso getCorso();

    void setCorso(Corso corso);
}
