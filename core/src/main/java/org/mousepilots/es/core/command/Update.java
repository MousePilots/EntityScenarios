/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.command;

import org.mousepilots.es.core.model.impl.ref.SerializableReference;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.model.proxy.ProxyAspect;
import org.mousepilots.es.core.scenario.ServerContext;
import org.mousepilots.es.core.util.Function;

/**
 * Represents an update (change of attribute-value) to an entity- or embeddable.
 * <p>
 * The association of an embeddable {@code e} to another managed instance via
 * some {@link AttributeES} {@code a} is bound by the following rules:
 * <ol>
 * <li>moving {@code e} from one container {@code c'} to another ({@code c})is
 * NOT allowed</li>
 * <li>by the previous rule, {@code e} MUST be a creation within the same
 * transaction of the association</li>
 * </ol>
 * To uniquely identify the container we must store in the update corresponding
 * to the association:
 * <ul>
 * <li>
 * if {@code c} is a creation: a reference to the create-command {@code cc} of
 * c. The container is found on the server using {@link cc#getSubject()}
 * </li>
 * <li>otherwise: a copy of the complete container-path from the identifiable
 * root up to and including c BEFORE the association</li>
 * </ul>
 * </p>
 *
 * @author geenenju
 * @param <E>
 * @param <TD>
 * @param <A>
 * @param <AD>
 * @param <SR>
 */
public abstract class Update<E, TD extends ManagedTypeES<E>, A, AD extends AttributeES<? super E, A>, SR extends SerializableReference> extends AbstractCommand<E, TD> implements SubjectResolver<E> {

     private int attributeOrdinal;

     private Create<E, TD> createCommand;

     private SR reference;
     
     private UpdateAttribute<E,A,AD> updateAttribute;
     

     protected Update() {
     }

     private Update(ProxyAspect proxyAspect) {
          super(proxyAspect.getEntityManager(), (TD) proxyAspect.getType());
     }

     protected Update(Proxy<E> proxy, AD attribute, Function<Proxy<E>, SR> referenceConstructor, UpdateAttribute<E,A,AD> updateAttribute) {
          this(proxy.__getProxyAspect());
          this.attributeOrdinal = attribute.getOrdinal();
          final ProxyAspect<E> proxyAspect = proxy.__getProxyAspect();
          if (proxyAspect.isCreated()) {
               createCommand = (Create) proxyAspect.getCreate();
          } else {
               reference = referenceConstructor.apply(proxy);
          }
          this.updateAttribute=updateAttribute;
     }

     
     protected final boolean isSubjectCreated() {
          return createCommand != null;
     }

     protected final Create<E, TD> getCreateCommand() {
          return createCommand;
     }

     protected final SR getReference() {
          return reference;
     }

     public final UpdateAttribute<E, A, AD> getUpdateAttribute() {
          return updateAttribute;
     }

     public final AD getAttribute() {
          return (AD) AbstractMetamodelES.getInstance().getAttribute(attributeOrdinal);
     }

     @Override
     public final CRUD getOperation() {
          return CRUD.UPDATE;
     }

     @Override
     protected final void doExecuteOnClient() {
          this.updateAttribute.executeOnClient(this);
     }

     @Override
     protected final void doUndo() {
          this.updateAttribute.undo(this);
     }

     @Override
     protected final void doRedo() {
          this.updateAttribute.redo(this);
     }
     
     @Override
     public final void executeOnServer(ServerContext serverContext){
          if(createCommand==null){
               super.setRealSubject(resolveSubject(serverContext));
          } else {
               super.setRealSubject(createCommand.getRealSubject());
          }
          this.updateAttribute.executeOnServer(this, serverContext);
     }
     

}
