/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.singular;

import org.mousepilots.es.core.command.UpdateAttribute;
import org.mousepilots.es.core.command.OwnedSetter;
import org.mousepilots.es.core.model.AttributeES;

/**
 *
 * @author geenenju
 * @param <E>
 * @param <A>
 * @param <AD>
 */
public abstract class AbstractUpdateAttribute<E, A, AD extends AttributeES<? super E, A>> implements UpdateAttribute<E, A, AD>{

     private transient OwnedSetter<A> superSetter;

     protected OwnedSetter<A> getSuperSetter() {
          return superSetter;
     }

     protected AbstractUpdateAttribute(){}
     
     public AbstractUpdateAttribute(OwnedSetter<A> superSetter) {
          this.superSetter = superSetter;
     }
     
     
     
}
