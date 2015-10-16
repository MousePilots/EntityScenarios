/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import javax.persistence.metamodel.Attribute;

/**
 *
 * @author clevenro
 */
public interface AttributeES<X, Y> extends Attribute<X, Y>
{
    
    boolean isReadOnly();
    
    boolean isAssociation(AssocationTypeES type);
    
    AssociationES getAssociation(AssocationTypeES type);

    @Override
    public MemberES getJavaMember();
    
//    Wrapper<Y> wrap(Y value); Mogelijk tijdens de implementatie terughalen.
}
