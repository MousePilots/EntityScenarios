/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasType;

/**
 * A serializable reference to a managed instance
 * @author geenenju
 * @param <T>
 * @param <TD>
 */
public abstract class SerializableReference<T,TD extends TypeES<T>> extends HasType<TD>{

     protected SerializableReference() {
     }

     protected SerializableReference(int typeOrdinal) {
          super(typeOrdinal);
     }

     protected SerializableReference(TD type) {
          super(type);
     }
     
     
     /**
      * 
      * @param entityManager
      * @param typeToClientIdToServerId
      * @return 
      */
     public abstract T get(EntityManager entityManager,Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToServerId);
     
}
