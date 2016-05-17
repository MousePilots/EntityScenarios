package org.mousepilots.es.test.shared;


import java.util.Set;
import org.junit.Assert;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.command.CreateEntity;
import org.mousepilots.es.core.model.AssociationES;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EntityManagerFactory;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.test.domain.MetamodelImpl;
import org.mousepilots.es.test.domain.entities.ManagerAccount_ES;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jgeenen
 */

public class TestMetaModel extends AbstractTest{

    private static final MetamodelES METAMODEL;
    static {
        MetamodelImpl.init();
        METAMODEL = MetamodelImpl.INSTANCE;
    }

    public TestMetaModel() {
        super(TestMetaModel.class);
    }
    
    
    
    private final AttributeVisitor<Void, Void> attributeTester = new AttributeVisitor<Void, Void>(){
        
        private void assertAssociationDefinitionCorrect(AttributeES a, AssociationTypeES associationType, TypeES t){
            final AssociationES association = a.getAssociation(associationType);
            Assert.assertTrue(a + " must have a value association if and only if its " + associationType + "-type is managed",
                (t instanceof ManagedTypeESImpl) == (association!=null)
            );
            if(association!=null){
                final AssociationES inverse = association.getInverse();
                Assert.assertTrue(association + " is no owner and not bidirectional", association.isOwner() || association.isBiDirectional());
                if(inverse!=null){
                    Assert.assertTrue("the inverse of the inverse of " + association + " does resolve correctly", inverse.getInverse()==association);
                }
            }
        }
        
        private Void visitPluralAttribute(PluralAttributeES pluralAttributeESImpl){
            assertAssociationDefinitionCorrect(pluralAttributeESImpl, AssociationTypeES.VALUE, pluralAttributeESImpl.getElementType());
            return null;
        }
        @Override
        public Void visit(SingularAttributeES a, Void arg) {
            assertAssociationDefinitionCorrect(a,  AssociationTypeES.VALUE, a.getType());
            return null;
        }

        @Override
        public Void visit(CollectionAttributeES a, Void arg) {
            return visitPluralAttribute(a);
        }

        @Override
        public Void visit(ListAttributeES a, Void arg) {
            return visitPluralAttribute(a);
        }

        @Override
        public Void visit(SetAttributeES a, Void arg) {
            return visitPluralAttribute(a);
        }

        @Override
        public Void visit(MapAttributeES a, Void arg){
            return visitPluralAttribute(a);
        }
    };

    public void testMetaModel(){
        final Set<ManagedTypeESImpl<?>> managedTypes = (Set) METAMODEL.getManagedTypes();
        EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();
        final EntityManagerImpl entityManager = (EntityManagerImpl) entityManagerFactory.createEntityManager();
        Command command = new CreateEntity(entityManager,ManagerAccount_ES.__TYPE,null);
        command.executeOnClient();
        command.undoOnClient();
        command.redoOnClient();
        for(ManagedTypeESImpl t : managedTypes){
            if(t.isInstantiable()){
                final Object javaTypeInstance = t.createInstance();
                final Proxy proxy = t.createProxy();
                proxy.__getProxyAspect().setManagedMode(false);
                Assert.assertNotNull("cannot instantiate of " + t.getJavaType(), javaTypeInstance);
                Assert.assertNotNull("cannot instantiate proxy for " + t.getJavaType() + " of class " + t.getProxyJavaType(), t.createProxy());
            }
            final Set<AttributeESImpl> attributes = t.getAttributes();
            for(AttributeESImpl a : attributes){
                a.accept(attributeTester, null);
            }
        }
    }
    
}
