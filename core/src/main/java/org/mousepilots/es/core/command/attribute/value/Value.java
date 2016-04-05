/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import java.util.Collection;
import java.util.List;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.HasTypeImpl;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;


public abstract class Value<CV,SV,EV, TD extends TypeES> extends HasTypeImpl<TD>{
     
     public static <CV, L extends List<CV>> L collectClientValues(Collection<? extends Value<CV,?,?,?>> values, L clientValues){
          for(Value<CV,?,?,?> value : values){
               clientValues.add(value.getClientValue());
          }
          return clientValues;
     }
     
     @GwtIncompatible
     public static <SV, L extends List<SV>> L collectServerValues(ServerContext serverContext, Collection<? extends Value<?,SV,?,?>> values, L serverValues){
          for(Value<?,SV,?,?> value : values){
               serverValues.add(value.getServerValue(serverContext));
          }
          return serverValues;
     }
     
     
     private static long ID_GENERATOR = 0;
     
     private long id;

     private transient CV clientValue;
     
     private transient SV serverValue;
     
     @GwtIncompatible
     protected abstract SV decode(EV encodedServerValue,ServerContext serverContext);
     
     protected abstract EV getEncodedServerValue();

     protected Value(){}

     protected Value(int typeOrdinal, CV clientValue){
          super(typeOrdinal);
          this.clientValue = clientValue;
          this.id=ID_GENERATOR++;
     }
     
     
     public final CV getClientValue(){
          return clientValue;
     }
     
     @GwtIncompatible
     public final SV getServerValue(ServerContext serverContext){
          final EV encodedServerValue = getEncodedServerValue();
          if(serverValue==null && encodedServerValue!=null){
               this.serverValue = decode(encodedServerValue,serverContext);
          }
          return serverValue;
     }
     
     public abstract <R,A> R accept(ValueVisitor<R,A> valueVisitor, A arg);

     @Override
     public int hashCode() {
          int hash = 5;
          hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
          return hash;
     }

     @Override
     public boolean equals(Object obj) {
          if (obj == null) {
               return false;
          }
          if (getClass() != obj.getClass()) {
               return false;
          }
          final Value<?, ?, ?, ?> other = (Value<?, ?, ?, ?>) obj;
          if (this.id != other.id) {
               return false;
          }
          return true;
     }
     
     
}
