/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.container.Container;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EmbeddableTypeES;

/**
 *
 * @author geenenju
 * @param <T>
 */
public interface EmbeddableTypeCommand<T> extends Command<T, EmbeddableTypeES<T>>, HasContainer {

     @Override
     public default void setContainer(Container container) {
          final Container existing = getContainer();
          if (existing != null && container != null) {
               throw new IllegalArgumentException("cannot overwrite existing container " + existing);
          }
     }

}
