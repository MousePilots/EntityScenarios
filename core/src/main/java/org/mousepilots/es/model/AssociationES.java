/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

/**
 * 
 * @author ernsteni
 */
public interface AssociationES {
    
    AssocationTypeES getAssociationType();
    
    AttributeES getSourceAttribute();
    
    AssociationES getInverse();
    
    boolean isOwner();
    boolean isBiDirectional();    
}
