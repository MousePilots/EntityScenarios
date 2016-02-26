/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.change2;

import java.io.Serializable;
import java.util.Map;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.change.abst.AbstractChange;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityManager;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 * @author jgeenen
 * @param <X> the attribute-owner's java-type
 * @param <Y> the attribute's java-type
 * @param <A> the {@link AttributeES}-subclass
 */
public abstract class Update<X, Y, A extends AttributeES< ? super X, Y>> extends AbstractChange
{
     private static final TypeVisitor<SerializableReference,Proxy> REFERENCE_CREATOR = new TypeVisitor<SerializableReference, Proxy>()
     {

          @Override
          public SerializableReference visit(BasicTypeES t, Proxy arg) {
               throw new UnsupportedOperationException("Not supported");
          }

          @Override
          public SerializableReference visit(EmbeddableTypeES t, Proxy arg) {
               return new EmbeddableReference(arg);
          }

          @Override
          public SerializableReference visit(MappedSuperclassTypeES t, Proxy arg) {
               return new IdentifiableReference(arg);
          }

          @Override
          public SerializableReference visit(EntityTypeES t, Proxy arg) {
               return new IdentifiableReference(arg);
          }
     };

     private int attributeOrdinal;
     
     private volatile Proxy<X> transientOwner;

     private SerializableReference<X,?> serializableOwnerReference;

     protected Update() {
          super();
     }

     protected Update(Proxy<X> owner, A attribute)
     {
          super(owner.__getProxyAspect().getType());
          this.attributeOrdinal = attribute.getOrdinal();
          this.transientOwner = owner;
          serializableOwnerReference = (SerializableReference<X, ?>) getType().accept(REFERENCE_CREATOR, owner);
     }

     /**
      * Gets the client-side transient owner whose attribute is changed by {@code this}. 
      * <strong>This reference is lost upon serialization.</strong>
      * @return a reference to the client-side transient owner: {@code null} after deserialization
      */
     public final Proxy<X> getTransientOwner(){
          return transientOwner;
     }
     
     @Override
     public final CRUD operation() {
          return CRUD.UPDATE;
     }
     
     /**
      * @return the updated attribute
      */
     public final A getAttribute() {
          return (A) AbstractMetamodelES.getInstance().getAttribute(attributeOrdinal);
     }
     
     /**
      * Finds the owner managed by {@code entitymanager} for which {@code this} update recorded an attribute-change: 
      * either an entity or an embeddable. Intended for server-side use.
      * @param entityManager
      * @param typeToClientIdToServerId
      * @return the entity or embeddable
      */
     public final X findOwner(EntityManager entityManager,Map<IdentifiableTypeES, Map<Serializable, Serializable>> typeToClientIdToServerId){
          return this.serializableOwnerReference.get(entityManager, typeToClientIdToServerId);
     }


}
