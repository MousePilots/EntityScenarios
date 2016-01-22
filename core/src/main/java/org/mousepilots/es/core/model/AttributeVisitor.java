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
