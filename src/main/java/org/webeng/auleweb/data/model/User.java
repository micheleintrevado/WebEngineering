package org.webeng.auleweb.data.model;

import it.univaq.f4i.iw.framework.data.DataItem;

/**
 *
 * @author Giuseppe Della Penna
 */
public interface User extends DataItem<Integer> {

    String getUsername();
    
    void setUsername(String username);

    String getPassword();
    
    void setPassword(String password);

}
