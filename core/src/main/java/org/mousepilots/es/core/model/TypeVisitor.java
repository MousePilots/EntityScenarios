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
public interface TypeVisitor<R> {
    
    R visit(BasicTypeES t);
    
    R visit(EmbeddableTypeES t);
    
    R visit(MappedSuperclassTypeES t);
    
    R visit(EntityTypeES t);
}