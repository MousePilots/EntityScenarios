package org.mousepilots.es.model;

import javax.persistence.metamodel.Attribute;

/**
 * Represents an attribute of a certain {@link TypeES}.
 * @param <T> The represented type that contains the attribute.
 * @param <TA> The type of the represented attribute.
 * @see Attribute
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
public interface AttributeES<T, TA> extends Attribute<T, TA>
{
    /**
     * Check if {@code this} attribute is read only, meaning it only has
     * a getter method.
     * @return {@code true} if {@code this} attribute is read only.
     * {@code false} otherwise.
     */
    boolean isReadOnly();
    
    /**
     * Check if {@code this} attribute is an association based on the {@link AssociationTypeES}.
     * @param type The {@link AssociationTypeES} type for the association to check.
     * @return {@code true} if {@code this} attribute is an association of the specified {@code type}.
     * {@code false} otherwise.
     */
    boolean isAssociation(AssociationTypeES type);
    
    /**
     * Get the {@link AssocationES} for {@code this} attribute of the specified {@link AssociationTypeES}.
     * @param type The {@link AssociationTypeES} for the {@link AssociationES} to get.
     * @return The {@link AssociationES} for {@code this} attribute for the specified {@link AssociationTypeES} if any.
     * Otherwise {@code null} is returned.
     */
    AssociationES getAssociation(AssociationTypeES type);

    /**
     * Get the {@link MemberES} for the represented attribute.
     * @return The {@link MemberES} for {@code this} attribute.
     */
    @Override
    public MemberES getJavaMember();
    
//    Wrapper<Y> wrap(Y value); Mogelijk tijdens de implementatie terughalen.
}