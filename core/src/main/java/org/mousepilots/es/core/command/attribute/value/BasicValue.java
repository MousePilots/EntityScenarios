/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command.attribute.value;

import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.scenario.ServerContext;

/**
 *
 * @author geenenju
 * @param <T>
 */
public class BasicValue<T> extends Value<T,T,HasValue<T>, BasicTypeESImpl<T>> {
     
     private HasValue<T> encodedServerValue;
     
     private BasicValue(){}

     BasicValue(BasicTypeESImpl<T> type, T value) {
          super(type.getOrdinal(), value);
          encodedServerValue = type.wrap(value);
     }

     @Override
     protected HasValue<T> getEncodedServerValue() {
          return encodedServerValue;
     }

     @Override
     protected T decode(HasValue<T> encodedServerValue, ServerContext serverContext) {
          return HasValue.getValueNullSafe(encodedServerValue);
     }

     @Override
     public <R, A> R accept(ValueVisitor<R, A> valueVisitor, A arg) {
          return valueVisitor.visit(this, arg);
     }
     
     
     
}
