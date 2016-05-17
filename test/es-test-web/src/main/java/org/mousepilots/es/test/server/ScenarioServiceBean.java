/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.test.server.domain.mmx.JPA;
import org.mousepilots.es.test.client.ScenarioService;

/**
 *
 * @author geenenju
 */
public class ScenarioServiceBean implements ScenarioService{
    

    @Override
    public  HasValue get(int typeOrdinal, HasValue id) {
        final EntityManager entityManager = JPA.createEntityManager();
        final EntityTypeES entityType = (EntityTypeES) AbstractMetamodelES.getInstance().getType(typeOrdinal);
        final Class javaType = entityType.getJavaType();
        final Object entity = entityManager.find(javaType, id.getValue());
        final HasValue wrapped = entityType.wrap(entity);
        entityManager.close();
        return wrapped;
    }

    @Override
    public void submit(List<Command> commands){
        final EntityManager entityManager = JPA.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final ServerContextImpl serverContext = new ServerContextImpl(entityManager);
        commands.forEach(c -> c.executeOnServer(serverContext));
        transaction.commit();
        entityManager.close();
    }
    
}
