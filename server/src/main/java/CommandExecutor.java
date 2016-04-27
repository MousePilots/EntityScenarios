
import javax.persistence.EntityManager;
import org.mousepilots.es.core.command.CommandVisitor;
import org.mousepilots.es.core.command.Create;
import org.mousepilots.es.core.command.CreateEmbeddable;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.command.DeleteEntity;
import org.mousepilots.es.core.command.UpdateEmbeddable;
import org.mousepilots.es.core.command.UpdateEntity;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.scenario.ServerContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ap34wv
 */
public class CommandExecutor implements CommandVisitor<Void, ServerContext>{

    private <T> void visitCreate(Create<T,?> create){
        final T newInstance = create.getType().createInstance();
        create.setRealSubject(newInstance);
    }
    
    @Override
    public <E> Void visit(CreateEmbeddable<E> create, ServerContext serverContext) {
        visitCreate(create);
        serverContext.onExecuteOnServer(create);
        return null;
    }

    @Override
    public <E, ID> Void visit(CreateEntity<E, ID> create, ServerContext serverContext) {
        visitCreate(create);
        final E entity = create.getRealSubject();
        final EntityManager entityManager = serverContext.getEntityManager();
        if (!create.getIdAttribute().isGenerated()) {
            create.assignId(entity);
        }
        entityManager.persist(entity);
        serverContext.onExecuteOnServer(create);
        return null;
    }

    @Override
    public <E, A, AD extends AttributeES<? super E, A>> Void visit(UpdateEmbeddable<E, A, AD> update, ServerContext serverContext) {
        
        return null;
    }

    @Override
    public <E, ID, V, A, AD extends AttributeES<? super E, A>> Void visit(UpdateEntity<E, ID, V, A, AD> update, ServerContext serverContext) {
        
        return null;
    }

    @Override
    public <E, ID> Void visit(DeleteEntity<E, ID> delete, ServerContext serverContext) {
        
        return null;
    }
    
}
