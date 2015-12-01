package org.mousepilots.es.change.abst;

import java.io.Serializable;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.model.AssociationES;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.IdentifiableTypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 */
public abstract class AbstractNonIdentifiableUpdate extends AbstractChange
{

   private Dto container;
   private Dto updated;
   private HasValue containerId;
   private int containerAttributeOrdinal;
   private int updatedAttributeOrdinal;

   protected AbstractNonIdentifiableUpdate()
   {

   }

   protected AbstractNonIdentifiableUpdate(Dto container, AttributeES containerAttribute, Dto updated, AttributeES updatedAttribute)
   {
      super(updatedAttribute.getDeclaringType());
      // validations
      if (container == null)
      {
         throw new IllegalArgumentException("container==null");
      }
      //possibility of having embeddable as parent instead of identifiable
      final IdentifiableTypeES containerType = container.getType();
      if (containerType != containerAttribute.getDeclaringType())
      {
         throw new IllegalArgumentException("containerAttribute " + containerAttribute + " does not occur on container-type" + container.getType());
      }

      final AssociationES association = containerAttribute.getAssociation(AssociationTypeES.VALUE);
      if (association == null)
      {
         throw new IllegalArgumentException(containerAttribute + " represents no value association");
      }
      // TODO :
//      final TypeES associationTargetType = association.;
//      final TypeES updatedType = updatedAttribute.getDeclaringType();
//      if (associationTargetType.getOrdinal() != updatedType.getOrdinal())
//      {
//         throw new IllegalArgumentException("containerAttribute's association's targetType" + associationTargetType + " does not match updatedAttribute's owner " + updatedType);
//      }
//      if (updated.getType() != updatedType)
//      {
//         throw new IllegalArgumentException(updatedAttribute + " does not occur on " + updated);
//      }

      //set simple values
      this.updated = updated;
      this.updatedAttributeOrdinal = updatedAttribute.getOrdinal();
      this.containerAttributeOrdinal = containerAttribute.getOrdinal();

      //set container XOR containerId
      //final AttributeES containerIdAttribute = containerType.getId();
//      if (containerIdAttribute == null)
//      {
//         this.container = container;
//      }
//      else
//      {
//         this.containerId = containerIdAttribute.wrapForDTO(container.getIdValue());
//      }
   }

   @Override
   public final CRUD operation()
   {
      return CRUD.UPDATE;
   }

   public final Dto getUpdated()
   {
      return updated;
   }

   public final AttributeES getUpdatedAttribute()
   {
       return null;
      //return AbstractMetaModelES.getInstance().getAttribute(updatedAttributeOrdinal);
   }

   /**
    * If the updated embeddable is contained in another embeddable: said other embeddable, otherwise {@code null}
    *
    * @return
    */
   public Dto getContainer()
   {
      return this.container;
   }

   /**
    * @return fast convenience equiavlent of {@code getContainer().getType().isIdentifiable()} represents an identifiable type
    */
   public boolean hasIdentifiableContainer()
   {
      return this.containerId != null;
   }

   /**
    * If the updated embeddable is contained in an identifiable said identifiable, otherwise {@code null}
    *
    * @return
    */
   public Serializable getContainerId()
   {
      return this.containerId == null ? null : (Serializable) containerId.getValue();
   }

   /**
    * @return the container's attribute on which the updated embeddable lives, never {@code null}
    */
   public final AttributeES getContainerAttribute(){
       return null;
//      return AbstractMetaModel.getInstance().getAttribute(containerAttributeOrdinal);
   }
}