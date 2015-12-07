package org.mousepilots.es.change.abst;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.CRUD;
import org.mousepilots.es.change.impl.EmbeddableAndAttribute;
import org.mousepilots.es.model.AttributeES;
import org.mousepilots.es.model.DtoType;
import org.mousepilots.es.model.HasValue;
import org.mousepilots.es.model.TypeES;

/**
 * @author Jurjen van Geenen
 * @author Roy Cleven
 * @param <C>
 * @param <U>
 */
public abstract class AbstractNonIdentifiableUpdate<C, U> extends AbstractChange
{

   private C container;
   private U updated;
   private HasValue containerId;
   private AttributeES containerAttribute;
   private AttributeES updatedAttribute;
   private List<EmbeddableAndAttribute> containerEmbeddables;

   protected AbstractNonIdentifiableUpdate()
   {

   }

    public AbstractNonIdentifiableUpdate(C container, U updated, HasValue containerId, AttributeES containerAttribute, AttributeES updatedAttribute, TypeES type, DtoType dtoType, List<EmbeddableAndAttribute> containerEmbeddables) {
        super(type, dtoType);
        this.container = container;
        this.updated = updated;
        this.containerId = containerId;
        this.containerAttribute = containerAttribute;
        this.updatedAttribute = updatedAttribute;
        this.containerEmbeddables = containerEmbeddables;
    }

   @Override
   public final CRUD operation()
   {
      return CRUD.UPDATE;
   }

   public final U getUpdated()
   {
      return updated;
   }

   public final AttributeES getUpdatedAttribute()
   {
       return updatedAttribute;
   }

   /**
    * If the updated embeddable is contained in another embeddable: said other embeddable, otherwise {@code null}
    *
    * @return
    */
   public C getContainer()
   {
      return this.container;
   }
   
   /**
    * If the updated embeddable is contained in an identifiable said identifiable, otherwise {@code null}
    *
    * @return
    */
   public Serializable getContainerId()
   {
      return (Serializable) containerId.getValue();
   }

   /**
    * @return the container's attribute on which the updated embeddable lives, never {@code null}
    */
   public final AttributeES getContainerAttribute(){
       return containerAttribute;
   }

    public List<EmbeddableAndAttribute> getContainerEmbeddables() {
        return containerEmbeddables;
    }
}