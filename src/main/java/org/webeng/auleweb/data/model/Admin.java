/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.model;

import org.webeng.auleweb.framework.data.DataItem;
/**
 *
 * @author enric
 */
public interface Admin extends DataItem<Integer>{
    String getUserame();
    void setUsername(String username);
    
    String getPassword();
    void setPassword(String password);
    
    String getToken();
    void setToken(String token);
}
