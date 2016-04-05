/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.ref;

import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasTypeImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 * A serializable reference to a managed instance
 * @author geenenju
 * @param <T> the referenced instance's type
 * @param <TD> the referenced instance's typedescriptor's type
 */
public abstract class SerializableReference<T,TD extends TypeES<T>> extends HasTypeImpl<TD>{

     protected SerializableReference() {
     }

     protected SerializableReference(int typeOrdinal) {
          super(typeOrdinal);
     }

     protected SerializableReference(TD type) {
          super(type);
     }
     
     /**
      * Resolves the serialized reference on the server
      * @param serverContext
      * @return the managed reference
      */
     @GwtIncompatible
     public abstract T resolve(ServerContext serverContext);
     
}
