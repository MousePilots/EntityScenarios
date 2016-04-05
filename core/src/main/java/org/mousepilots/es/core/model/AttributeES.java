package org.mousepilots.es.core.model;

import java.util.Map;
import javax.persistence.metamodel.Attribute;


/**
 * Represents an attribute of a certain {@link TypeES}.
 *
 * @param <X> The represented type that contains the attribute.
 * @param <Y> The type of the represented attribute.
  * @see Attribute
 * @author Roy Cleven
 * @version 1.0, 20-10-2015
 */
public interface AttributeES<X, Y> extends Attribute<X, Y>, Comparable<AttributeES>, HasOrdinal {

    /**
     * @return whether or not {@code this} attribute is read only in which case it has not public setter.
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
     * @return the {@link TypeES} {@code t} for which {@code this.getJavaType().equals(t.getJavaType())}
     */
    TypeES<Y> getType();

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
     * @return the unmodifiable, non-{@code null} mapping {@link AssociationTypeES} &rarr; {@link AssociationES}
     */
    public Map<AssociationTypeES, AssociationES<X,Y>> getAssociations();

    @Override
    public MemberES<X,Y> getJavaMember();

    @Override
    public ManagedTypeES<X> getDeclaringType();
    
    /**
     * 
     * @param <R> the return type of the visitor
     * @param <A> the argument type of the visitor
     * @param visitor the visitor
     * @param arg the argument to be passed to the visitor
     * @return {@code visitor.accept(this,arg)}
     */    
    public <R,A> R accept(AttributeVisitor<R,A> visitor, A arg);
}