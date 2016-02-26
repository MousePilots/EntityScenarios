/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

/**
 *
 * @author jgeenen
 * @param <R>
 * @param <A>
 */
public interface AttributeVisitor<R,A>{
    
    R visit(SingularAttributeES a, A arg);
    
    R visit(CollectionAttributeES a, A arg);
    
    R visit(ListAttributeES a, A arg);
    
    R visit(SetAttributeES a, A arg);
    
    R visit(MapAttributeES a, A arg);
}
