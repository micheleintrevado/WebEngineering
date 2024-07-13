/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.webeng.auleweb.data.model.impl.proxy;


import org.webeng.auleweb.data.model.impl.CorsoImpl;
import org.webeng.auleweb.framework.data.DataItemProxy;
import org.webeng.auleweb.framework.data.DataLayer;

/**
 *
 * @author miche
 */
public class CorsoProxy extends CorsoImpl implements DataItemProxy{
    protected boolean modified;
    
    protected DataLayer dataLayer;
    
    public CorsoProxy(DataLayer dataLayer){
        super();
        this.dataLayer = dataLayer;
        this.modified = false;
    }
    
    @Override
    public void setNome(String n){
        super.setNome(n);
        this.modified = true;
    }
    
    @Override
    public void setKey(Integer key){
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
}
