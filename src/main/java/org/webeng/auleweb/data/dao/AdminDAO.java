/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.webeng.auleweb.data.dao;

import java.util.List;
import org.webeng.auleweb.framework.data.DataException;
import org.webeng.auleweb.data.model.Admin;

/**
 *
 * @author enric
 */
public interface AdminDAO {
    //metodo "factory" che permettono di creare
    //e inizializzare opportune implementazioni
    //delle interfacce del modello dati, nascondendo
    //all'utente tutti i particolari
    //factory method to create and initialize
    //suitable implementations of the data model interfaces,
    //hiding all the implementation details
    
    Admin createAdmin();

    Admin getAdmin(int admin_key) throws DataException;
    
    Admin getAdminByName(String username) throws DataException;
    
    List<Admin> getAdmins() throws DataException;

    void storeAdmin(Admin admin) throws DataException;

}
