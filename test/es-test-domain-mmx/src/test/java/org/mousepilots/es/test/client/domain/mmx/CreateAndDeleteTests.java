/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.client.domain.mmx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import org.junit.Assert;
import org.junit.Test;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.command.DeleteEntity;
import org.mousepilots.es.core.command.UpdateEntity;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityTransaction;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.util.SetUtils;

/**
 *
 * @author geenenju
 */
public class CreateAndDeleteTests extends JPA {

    private final Set<EntityTypeESImpl> instantiableEntityTypesWithGeneratedIds = (Set) JPA.getMetaModel()
            .getEntities()
            .stream()
            .filter(et -> {
                EntityTypeESImpl etesi = (EntityTypeESImpl) et;
                return etesi.isInstantiable() && etesi.getId().isGenerated();
            })
            .collect(Collectors.toSet());

    private final AttributeVisitor<Boolean, Void> embeddableAttributeSelector = new AttributeVisitor<Boolean, Void>() {
        @Override
        public Boolean visit(SingularAttributeES a, Void arg) {
            return a.getType() instanceof EmbeddableTypeES;
        }

        @Override
        public Boolean visit(CollectionAttributeES a, Void arg) {
            return a.getElementType() instanceof EmbeddableTypeES;
        }

        @Override
        public Boolean visit(ListAttributeES a, Void arg) {
            return a.getElementType() instanceof EmbeddableTypeES;
        }

        @Override
        public Boolean visit(SetAttributeES a, Void arg) {
            return a.getElementType() instanceof EmbeddableTypeES;
        }

        @Override
        public Boolean visit(MapAttributeES a, Void arg) {
            return a.getKeyType() instanceof EmbeddableTypeES
                    || a.getElementType() instanceof EmbeddableTypeES;
        }

    };

    private boolean isEmbeddableAttribute(AttributeES a) {
        return (Boolean) a.accept(embeddableAttributeSelector, null);
    }

    private final Set<AttributeES> embeddableAttributes = new HashSet<>();

    private final Set<EntityTypeESImpl> entitiesWithEmbeddableAttributes = (Set) instantiableEntityTypesWithGeneratedIds
            .stream()
            .filter(
                    et -> ((Set<AttributeES>) et.getAttributes())
                    .stream()
                    .anyMatch(
                            a -> {
                                final boolean accepted = (Boolean) a.accept(embeddableAttributeSelector, null);
                                if (accepted) {
                                    embeddableAttributes.add(a);
                                }
                                return accepted;
                            }
                    )
            ).collect(Collectors.toSet());

    @Test
    public void createDeleteUndoRedoTest() {
        final long startTime = System.currentTimeMillis();
        for (EntityTypeESImpl entityType : this.instantiableEntityTypesWithGeneratedIds) {
            getLogger().log(Level.FINE, "testing create of {0}", entityType);
            final EntityManagerES entityManager = createEntityManagerES();
            final Proxy creation = (Proxy) entityManager.create(entityType);
            Assert.assertNotNull("failed to create entity of type " + entityType, creation);
            final Object id = entityType.getId().getJavaMember().get(creation);
            final Class javaType = entityType.getJavaType();
            Assert.assertEquals(creation, entityManager.find(javaType, id));
            final EntityTransaction transaction = entityManager.getTransaction();
            List<Command> commands = transaction.getCommands();
            Assert.assertEquals(1, commands.size());
            final Command create = commands.get(0);
            Assert.assertTrue("expected an instance of " + CreateEntity.class.toString(), create instanceof CreateEntity);
            Assert.assertTrue("expected " + entityType + " instead of " + create.getType(), create.getType() == entityType);
            Assert.assertTrue(creation.__getProxyAspect().isCreated());
            entityManager.remove(creation);
            Assert.assertTrue(creation.__getProxyAspect().isDeleted());
            Assert.assertNull(create + " must not be found after removal", entityManager.find(javaType, id));

            commands = transaction.getCommands();
            Assert.assertEquals(2, commands.size());
            final Command delete = commands.get(1);
            Assert.assertTrue("expected an instance of " + DeleteEntity.class.toString(), delete instanceof DeleteEntity);
            Assert.assertTrue("expected " + entityType + " instead of " + delete.getType(), delete.getType() == entityType);

            //undo delete
            transaction.undo();
            Assert.assertEquals(1, transaction.getCommands().size());
            Assert.assertFalse(creation.__getProxyAspect().isDeleted());
            Assert.assertEquals(create + " must be found after undoing removal", creation, entityManager.find(javaType, id));

            //undo create
            transaction.undo();
            Assert.assertEquals(0, transaction.getCommands().size());
            Assert.assertFalse(creation.__getProxyAspect().isDeleted());
            Assert.assertFalse(creation.__getProxyAspect().isCreated());
            Assert.assertNull(create + " must not be found after undoing creation", entityManager.find(javaType, id));
        }
        getLogger().log(Level.INFO, "tested create on {0} entity types in {1} ms", new Object[]{instantiableEntityTypesWithGeneratedIds.size(), System.currentTimeMillis() - startTime});
    }

    @Test
    public void createDeleteExecuteTest() {
        //create the proxies
        final EntityManagerES entityManager = createEntityManagerES();
        List<Proxy> proxies = new ArrayList<>(this.instantiableEntityTypesWithGeneratedIds.size());
        for (EntityTypeESImpl entityType : this.instantiableEntityTypesWithGeneratedIds) {
            final Proxy proxy = (Proxy) entityManager.create(entityType);
            proxies.add(proxy);
        }

        //
        CommandProcessor commandProcessor = new CommandProcessor();
        commandProcessor.process(entityManager.getTransaction().getCommands());
        final Map<EntityType, Object> entityType2serverId = new HashMap<>();
        for (Proxy proxy : proxies) {
            final EntityTypeESImpl entityType = (EntityTypeESImpl) proxy.__getProxyAspect().getType();
            final Object clientId = entityType.getId().getJavaMember().get(proxy);
            Assert.assertNotNull(clientId);
            final Object serverId = commandProcessor.getServerContext().getServerId(entityType, clientId);
            Assert.assertNotNull(serverId);
            entityType2serverId.put(entityType, serverId);
        }

        final EntityManager serverEntityManager = createEntityManager();
        final javax.persistence.EntityTransaction serverTransaction = serverEntityManager.getTransaction();
        serverTransaction.begin();
        //find and delete each entity
        for (Map.Entry<EntityType, Object> entry : entityType2serverId.entrySet()) {
            final Object managedEntity = serverEntityManager.find(entry.getKey().getJavaType(), entry.getValue());
            Assert.assertNotNull(managedEntity);
            serverEntityManager.remove(managedEntity);
        }
        serverTransaction.commit();
    }

    @Test
    public void testUpdateEmbeddableAssociations() {
        final EntityManagerES entityManagerES = createEntityManagerES();

        final AttributeVisitor<Void, Object> embeddableAssociator = new AttributeVisitor<Void, Object>() {

            private Void visitJavaUtilCollectionAttribute(PluralAttributeES a, Object arg) {
                final Collection collection = (Collection) a.getJavaMember().get(arg);
                collection.add(entityManagerES.create((EmbeddableTypeES) a.getElementType()));
                return null;
            }

            @Override
            public Void visit(SingularAttributeES a, java.lang.Object arg) {
                a.getJavaMember().set(arg, entityManagerES.create((EmbeddableTypeES) a.getType()));
                return null;
            }

            @Override
            public Void visit(CollectionAttributeES a, java.lang.Object arg) {
                return visitJavaUtilCollectionAttribute(a, arg);
            }

            @Override
            public Void visit(ListAttributeES a, java.lang.Object arg) {
                return visitJavaUtilCollectionAttribute(a, arg);
            }

            @Override
            public Void visit(SetAttributeES a, java.lang.Object arg) {
                return visitJavaUtilCollectionAttribute(a, arg);
            }

            @Override
            public Void visit(MapAttributeES a, java.lang.Object arg) {
                return null;
            }

        };

        for (EntityTypeESImpl et : instantiableEntityTypesWithGeneratedIds) {
            final SortedSet<AttributeES> attributes = et.getAttributes();
            final Set<AttributeES> embeddableAttributesEt = SetUtils.intersection(attributes, this.embeddableAttributes);
            if (!embeddableAttributesEt.isEmpty()) {
                final Object entity = entityManagerES.create(et);
                for (AttributeES embeddableAttribute : embeddableAttributesEt) {
                    embeddableAttribute.accept(embeddableAssociator, entity);
                }
            }
        }
        final List<Command> commands = entityManagerES.getTransaction().getCommands();
        final CommandProcessor commandProcessor = new CommandProcessor();
        commandProcessor.process(commands);
        final UpdateEntity ue = (UpdateEntity) commands.stream().filter(c -> c instanceof UpdateEntity).collect(Collectors.toList()).get(0);
        final EntityTypeES updatedEntityType = (EntityTypeES) ue.getType();
        final Object serverId = commandProcessor.getServerContext().getServerId(updatedEntityType, ue.getId());

        final EntityManager serverEntityManager = createEntityManager();
        final javax.persistence.EntityTransaction serverTransaction = serverEntityManager.getTransaction();
        serverTransaction.begin();
        final Object managedEntity = serverEntityManager.find(updatedEntityType.getJavaType(), serverId);
        final AttributeES attribute = ue.getAttribute();
        if (attribute instanceof PluralAttributeES) {
            final Collection collection = (Collection) attribute.getJavaMember().get(managedEntity);
            collection.clear();
        } else {
            attribute.getJavaMember().set(managedEntity, null);
        }
        serverTransaction.commit();

    }
}
