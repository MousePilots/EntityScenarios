/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

/**
 *
 * @author jgeenen
 */
public interface AttributeVisitor{
    
    void visit(SingularAttributeES a);
    
    void visit(CollectionAttributeES a);
    
    void visit(ListAttributeES a);
    
    void visit(SetAttributeES a);
    
    void visit(MapAttributeES a);
}
