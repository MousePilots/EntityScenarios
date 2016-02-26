/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

/**
 *
 * @author jgeenen
 */
public abstract class HasSeal {
    
    private boolean sealed=false;
    
    protected void assertNotSealed() throws IllegalStateException{
        if (isSealed()) {
            throw new IllegalStateException(this + " is sealed");
        }
    }
    

    public boolean isSealed() {
        return sealed;
    }
    
    public void seal(){
        sealed=true;
    }
}
