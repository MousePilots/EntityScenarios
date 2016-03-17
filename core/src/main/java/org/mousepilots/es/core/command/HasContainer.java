/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.container.Container;

/**
 *
 * @author geenenju
 */
public interface HasContainer {
     
     void setContainer(Container container);
     
     Container getContainer();
}
