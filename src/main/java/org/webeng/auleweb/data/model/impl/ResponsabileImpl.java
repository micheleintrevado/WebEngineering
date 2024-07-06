/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import it.univaq.f4i.iw.framework.data.DataItemImpl;
import org.webeng.auleweb.data.model.Responsabile;

/**
 *
 * @author enric
 */
public class ResponsabileImpl extends DataItemImpl<Integer> implements Responsabile {

    public String nome;
    public String email;

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

}
