package org.webeng.auleweb.data.model;

import java.util.List;
import org.webeng.auleweb.framework.data.DataItem;

/**
 *
 * @author enric
 */
public interface Responsabile extends DataItem<Integer>{
    String getNome();
    void setNome(String nome);
    
    String getEmail();
    void setEmail(String email);
    
    List<Aula> getAule();
    void setAule(List<Aula> aula);
    void addAula(Aula aula);
    
    List<Evento> getEventi();
    void setEventi(List<Evento> eventi);
    void addEvento(Evento evento);

}
