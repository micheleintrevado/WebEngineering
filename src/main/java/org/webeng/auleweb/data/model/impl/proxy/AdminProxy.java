/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;

import it.univaq.f4i.iw.framework.data.DataItemProxy;
import it.univaq.f4i.iw.framework.data.DataLayer;
import org.webeng.auleweb.data.model.impl.AdminImpl;

/**
 *
 * @author enric
 */
public class AdminProxy extends AdminImpl implements DataItemProxy {
    
    protected boolean modified;
    
    protected DataLayer dataLayer;
    
    public AdminProxy(DataLayer dataLayer) {
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public boolean isModified() {
        return modified;
    }
    
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }
    
    @Override
    public void setUsername(String u) {
        super.setUsername(u);
        this.modified = true;
    }
    
    @Override
    public void setPassword(String p) {
        super.setPassword(p);
        this.modified = true;
    }
    
    @Override
    public void setToken(String token) {
        super.setToken(token);
        this.modified = true;
    }
    
}
