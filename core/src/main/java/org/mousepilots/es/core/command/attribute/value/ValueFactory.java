/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import java.util.Collection;
import static javax.management.Query.value;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.model.impl.Constructor;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author geenenju
 */
public class ValueFactory{
     
     private ValueFactory(){}
     
     private static final TypeVisitor<Value,Object> VALUE_CREATOR = new TypeVisitor<Value, Object>() {

          @Override
          public Value visit(BasicTypeES t, Object arg) {
               return arg==null ? null : new BasicValue((BasicTypeESImpl) t, arg);
          }

          @Override
          public Value visit(EmbeddableTypeES t, Object arg) {
               if(arg==null){
                    return null;
               } else {
                    Proxy proxy = (Proxy) arg;
                    if(proxy.__getProxyAspect().isCreated()){
                         return new NewManagedValue(proxy);
                    } else {
                         return new ExistingEmbeddableValue(proxy);
                    }
               }
          }

          @Override
          public Value visit(MappedSuperclassTypeES t, Object arg) {
               throw new UnsupportedOperationException("Not supported yet.");
          }

          @Override
          public Value visit(EntityTypeES t, Object arg) {
               if(arg==null){
                    return null;
               } else {
                    Proxy proxy = (Proxy) arg;
                    if(proxy.__getProxyAspect().isCreated()){
                         return new NewManagedValue(proxy);
                    } else {
                         return new ExistingEntityValue(proxy);
                    }
               }
          }
     };
     
     /**
      * Creates a {@link Value}-wrapper for the a specified {@code type} and {@code value} thereof
      * @param <T>
      * @param type
      * @param value
      * @return the wrapper
      */
     public static <T> Value<T,T,?,?> create(TypeES<T> type, T value){
          return type.accept(VALUE_CREATOR, value);
     }
     
     /**
      * 
      * @param <T>
      * @param <C>
      * @param type
      * @param values
      * @param retvalConstructor
      * @return 
      */
     public static <T,C extends Collection<Value<T,T,?,?>>> C create(TypeES<T> type, Collection<T> values, Constructor<C> retvalConstructor){
         final C retval = retvalConstructor.invoke();
         for(T value : values){
             retval.add(create(type, value));
         }
         return retval;
     }
     
     
}
