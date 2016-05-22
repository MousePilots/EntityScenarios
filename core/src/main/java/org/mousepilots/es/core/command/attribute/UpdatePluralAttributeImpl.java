/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute;

import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 *
 * @author jgeenen
 */
public abstract class UpdatePluralAttributeImpl<E, EL, A, AD extends PluralAttributeES<? super E, A, EL>, MS> implements UpdatePluralAttribute<E, EL, A, AD, MS>{

    private transient boolean modificationOnServerInitialized;
    
    private transient MS modificationOnServer;
    
    protected UpdatePluralAttributeImpl(){}

    @GwtIncompatible
    protected abstract MS doGetModificationOnServer(ServerContext serverContext);
    
    @Override @GwtIncompatible
    public final MS getModificationOnServer(ServerContext serverContext) {
        if(!modificationOnServerInitialized){
            this.modificationOnServer = doGetModificationOnServer(serverContext);
            modificationOnServerInitialized = true;
        }
        return modificationOnServer;
    }
    
    
    
    
    
    
    
}
