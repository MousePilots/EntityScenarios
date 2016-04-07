/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.test.domain.MetamodelImpl;
import org.mousepilots.es.test.client.Persistency;

/**
 *
 * @author geenenju
 */
@Stateless
@LocalBean
public class PersistencyBean implements Persistency{
    
    static {
        MetamodelImpl.init();
    }
    
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public <T> HasValue<T> get(int typeOrdinal, HasValue id) {
        final EntityTypeES<T> entityType = (EntityTypeES) AbstractMetamodelES.getInstance().getType(typeOrdinal);
        final Class<T> javaType = entityType.getJavaType();
        final T entity = entityManager.find(javaType, id.getValue());
        return entityType.wrap(entity);
    }

    @Override
    public void submit(List<Command> commands){
        final ServerContextImpl serverContextImpl = new ServerContextImpl(entityManager);
        commands.forEach(c -> c.executeOnServer(serverContextImpl));
    }
    
}
