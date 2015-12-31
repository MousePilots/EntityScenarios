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
public interface TypeVisitor {
    
    void visit(BasicTypeES t);
    
    void visit(EmbeddableTypeES t);
    
    void visit(EntityTypeES t);
    
    void visit(MappedSuperclassTypeES t);
}
