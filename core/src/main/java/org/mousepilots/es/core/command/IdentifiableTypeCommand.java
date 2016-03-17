/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.IdentifiableTypeUtils;

/**
 *
 * @author geenenju
 * @param <T>
 * @param <ID>
 * @param <TD>
 */
public interface IdentifiableTypeCommand<T,ID,TD extends IdentifiableTypeES<T>> extends Command<T,TD>, SubjectResolver<T>{
     
     /**
      * @return {@code this}' id-attribute
      */
     default SingularAttributeES<? super T, ID> getIdAttribute(){
          return (SingularAttributeES) IdentifiableTypeUtils.getIdAttribute(getType());
     }
     
     /**
      * @return {@code this}' id-value
      */
     ID getId();     

     /**
      * Resolves the indentifiable {@code getRealSubject()} on the server
      * @param serverContext
      * @return 
      */
     @Override
     default T resolveSubject(ServerContext serverContext){
          return serverContext.find(getType(), getId());
     }
     
}
