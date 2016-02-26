/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

/**
 * 
 * @author jgeenen
 * @param <R> the return type of each {@code visit}-method
 * @param <A> the argument type of each {@code visit}-method: put {@link Void} for none
 */
public interface TypeVisitor<R,A> {
    
    R visit(BasicTypeES t, A arg);
    
    R visit(EmbeddableTypeES t, A arg);
    
    R visit(MappedSuperclassTypeES t, A arg);
    
    R visit(EntityTypeES t, A arg);
}
