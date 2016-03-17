/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.plural.collection;

import java.util.LinkedList;
import java.util.Collection;
import org.mousepilots.es.core.util.Function;

/**
 *
 * @author geenenju
 */
public enum CollectionOperation {
     ADD(){
          @Override
          public <EL, C extends Collection<EL>, V extends Collection<EL>> LinkedList<EL> executeOnClient(C collection, V values){
               return CollectionOperation.doExecuteOnClient(collection::add, values);
          }

          @Override
          public <EL, C extends Collection<EL>, V extends Collection<EL>> void executeOnServer(C collection, V values) {
               collection.addAll(values);
          }
          
          

          @Override
          public CollectionOperation inverse() {
               return REMOVE;
          }
     },
     
     REMOVE(){

          @Override
          public <EL, C extends Collection<EL>, V extends Collection<EL>> LinkedList<EL> executeOnClient(C collection, V values) {
               return CollectionOperation.doExecuteOnClient(collection::remove, values);
          }

          @Override
          public <EL, C extends Collection<EL>, V extends Collection<EL>> void executeOnServer(C collection, V values) {
               collection.removeAll(values);
          }

          @Override
          public CollectionOperation inverse() {
               return ADD;
          }
     };
     
     private static <EL, C extends Collection<EL>, V extends Collection<EL>> LinkedList<EL> doExecuteOnClient(Function<EL,Boolean> f, V values) {
          LinkedList<EL> retval = new LinkedList<>();
          for(EL element : values){
               if(f.apply(element)){
                    retval.add(element);
               }
          }
          return retval;
     }
     
     /**
      * Performs the action of add or removing the {@code values} from the {@code collection} on a client,
      * returning a list of values actually added/removed
      * @param <EL>
      * @param <C>
      * @param <V>
      * @param collection
      * @param values
      * @return the values actually added/removed
      */
     public abstract <EL,C extends Collection<EL>, V extends Collection<EL>> LinkedList<EL> executeOnClient(C collection, V values);
     
     /**
      * Performs the action of add or removing the {@code values} from the {@code collection} on a server.
      * @param <EL>
      * @param <C>
      * @param <V>
      * @param collection
      * @param values 
      */
     public abstract <EL,C extends Collection<EL>, V extends Collection<EL>> void executeOnServer(C collection, V values);
     
     public boolean isAdd(){
          return this==ADD;
     }
     
     public boolean isRemove(){
          return this==REMOVE;
     }
     
     public abstract CollectionOperation inverse();
     
     
}
