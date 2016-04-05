/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.maven.model.generator.model.attribute;

/**
 *
 * @author jgeenen
 */
public interface AttributeDescriptorVisitor<I,O> {
    
    O visit(SingularAttributeDescriptor singularAttributeDescriptor, I arg);
    
    O visit(CollectionAttributeDescriptor collectionAttributeDescriptor, I arg);
    
    O visit(ListAttributeDescriptor listAttributeDescriptor, I arg);
    
    O visit(SetAttributeDescriptor setAttributeDescriptor, I arg);
    
    O visit(MapAttributeDescriptor mapAttributeDescriptor, I arg);
    
}
