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
public interface AttributeVisitor<T>{
    
    T visit(SingularAttributeES a);
    
    T visit(CollectionAttributeES a);
    
    T visit(ListAttributeES a);
    
    T visit(SetAttributeES a);
    
    T visit(MapAttributeES a);
}
