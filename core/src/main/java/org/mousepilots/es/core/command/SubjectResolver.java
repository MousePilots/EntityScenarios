/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.GwtIncompatible;

/**
 * Resolves the server-side managed subject of a {@code Command}
 * @author geenenju
 * @param <E>
 */
public interface SubjectResolver<E> {
     
     /**
      * Resolves the server-side managed subject of a {@code Command}
      * @param serverContext
      * @return the managed subject
      */
    @GwtIncompatible
     E resolveSubject(ServerContext serverContext);
}
