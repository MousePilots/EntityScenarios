package org.mousepilots.es.model;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.change.Change;

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
     * @return the container with the wrapped {@code value}.
     */
    public HasValue wrapForChange(CA value);

    /**
     * Wraps attribute-{@code value} in a serializable container for a DTO.
     * @param value the value to wrapForChange.
     * @return the container with the wrapped {@code value}.
     */
    public HasValue wrapForDTO(TA value);

    @Override
    public MemberES getJavaMember();

    @Override
    public ManagedTypeES<T> getDeclaringType();
}