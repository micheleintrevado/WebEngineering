/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import it.univaq.f4i.iw.framework.data.DataItem;
import java.util.List;

/**
 *
 * @author enric
 */
public interface Aula extends DataItem<Integer> {
    String getNome();
    void setNome(String nome);
    
    String getLuogo();
    void setLuogo(String luogo);

    String getEdificio();
    void setEdificio(String edificio);
    
    String getPiano();
    void setPiano(String piano);
    
    int getCapienza();
    void setCapienza(int capienza);

    Responsabile getResponsabile();
    void setResponsabile(Responsabile responsabile);
    
    int getPreseElettriche();
    void setPreseElettriche(int preseElettriche);
    
    int getPreseRete();
    void setPreseRete(int preseRete);
    
    String getNote();
    void setNote(String note);    
    
    List<Attrezzatura> getAttrezzature();
    void setAttrezzature(Attrezzatura attrezzature);
    
    List<Gruppo> getGruppi();
    void setGruppi(List<Gruppo> gruppi);
}
