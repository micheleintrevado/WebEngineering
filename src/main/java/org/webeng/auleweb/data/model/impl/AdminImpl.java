/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl;

import org.webeng.auleweb.framework.data.DataItemImpl;
import org.webeng.auleweb.data.model.Admin;

/**
 *
 * @author miche
 */
public class AdminImpl extends DataItemImpl<Integer> implements Admin {

    private String username;
    private String password;
    private String token;

    public AdminImpl() {
        this.username = "";
        this.password = "";
        this.token = "";
    }

    public AdminImpl(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public String getUserame() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = this.password;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

}
