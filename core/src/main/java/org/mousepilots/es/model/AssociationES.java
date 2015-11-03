package org.mousepilots.es.model;

/**
 * Class representing an association between two entities.
 * @author Nicky Ernste
 * @version 1.0, 19-10-2015
 */
public interface AssociationES {
    
    /**
     * Get the type of the {@code this} association.
     * @return A value of the {@link AssociationTypeES} indicating the type of
     * {@code this} association.
     */
    AssociationTypeES getAssociationType();
    
    /**
     * Get the {@link AttributeES} that is the source of {@code this} association.
     * @return The attribute that is at the source of {@code this} association.
     */
    AttributeES getSourceAttribute();
    
    /**
     * Get the inversed {@link AssociationES} from the {@code this} association.
     * @return The inversed association from {@code this} association, if there is no
     * inverse {@code null} is returned.
     */
    AssociationES getInverse();
    
    /**
     * Check if {@code this} is the owning side of the association.
     * @return {@code true} if {@code this} is the owning side of the association.
     * {@code false} otherwise.
     */
    boolean isOwner();
    
    /**
     * Check if {@code this} association is bidirectionally mapped.
     * @return {@code true} if this association is mapped on both sides.
     * {@code false} otherwise.
     */
    boolean isBiDirectional();    
}