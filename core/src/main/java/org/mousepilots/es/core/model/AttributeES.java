package org.mousepilots.es.core.model;

import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.core.change.Change;

/**
 * Represents an attribute of a certain {@link TypeES}.
 *
 * @param <T> The represented type that contains the attribute.
 * @param <TA> The type of the represented attribute.
 * @param <CA> The type of the wrapped element(s) for a {@link Change}.
 * @see Attribute
 * @author Roy Cleven
 * @version 1.0, 20-10-2015
 */
public interface AttributeES<T, TA,CA> extends Attribute<T, TA>, Comparable<AttributeES>, HasOrdinal {

    /**
     * Check if {@code this} attribute is read only, meaning it only has a
     * getter method.
     *
     * @return {@code true} if {@code this} attribute is read only.
     * {@code false} otherwise.
     */
    boolean isReadOnly();

    /**
     * Check if {@code this} attribute is an association based on the
     * {@link AssociationTypeES}.
     *
     * @param type The {@link AssociationTypeES} type for the association to
     * check.
     * @return {@code true} if {@code this} attribute is an association of the
     * specified {@code type}. {@code false} otherwise.
     */
    boolean isAssociation(AssociationTypeES type);

    /**
     * Get the {@link AssocationES} for {@code this} attribute of the specified
     * {@link AssociationTypeES}.
     *
     * @param type The {@link AssociationTypeES} for the {@link AssociationES}
     * to get.
     * @return The {@link AssociationES} for {@code this} attribute for the
     * specified {@link AssociationTypeES} if any. Otherwise {@code null} is
     * returned.
     */
    AssociationES getAssociation(AssociationTypeES type);

    /**
     * Wraps attribute-{@code value} in a serializable container for a {@link Change}.
     * @param value the value to wrapForChange.
     * @param dtoType the type of {@link Dto} that needs to be wrapped.
     * @return the container with the wrapped {@code value}.
     */
    public HasValue wrapForChange(CA value, DtoType dtoType);

    /**
     * Wraps attribute-{@code value} in a serializable container for a DTO.
     * @param value the value to wrapForChange.
     * @param dtoType the type of {@link Dto} that needs to be wrapped.
     * @return the container with the wrapped {@code value}.
     */
    public HasValue wrapForDTO(TA value, DtoType dtoType);

    @Override
    public MemberES getJavaMember();

    @Override
    public ManagedTypeES<T> getDeclaringType();
    
    /**
     * Accept a change on this attribute.
     * @param <T> The type for this attribute.
     * @param visitor The change visitor to accept the change on.
     * @return Does not return an object, but null.
     */
    public <T> T accept(AttributeVisitor<T> visitor);
}