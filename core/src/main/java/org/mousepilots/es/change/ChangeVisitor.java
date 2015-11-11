/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.change;

import org.mousepilots.es.change.impl.Create;
import org.mousepilots.es.change.impl.Delete;

/**
 *
 * @author Roy Cleven
 */
public interface ChangeVisitor {
    
    void visit(Create create);
    
    void visit(Delete delete);
}