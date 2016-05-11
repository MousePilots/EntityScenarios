/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.util.Set;
import java.util.SortedSet;
import org.junit.Assert;
import org.junit.Test;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;

/**
 *
 * @author geenenju
 */
public class TypeTest extends AbstractTest{

    public TypeTest() {
        super(TypeTest.class);
    }
    
    
    
    @Test
    public void testManagedTypes(){
        final Set<ManagedTypeESImpl> entityTypes = (Set) getMetaModel().getManagedTypes();
        getLogger().info("testing " + entityTypes.size() + " managed types");
        for(ManagedTypeESImpl managedType : entityTypes){
            Assert.assertTrue(managedType==getMetaModel().managedType(managedType.getJavaType()));
            if(managedType.getProxyJavaType()!=null){
                Assert.assertTrue(managedType==getMetaModel().managedType(managedType.getProxyJavaType()));
            }
            Assert.assertTrue("attributes must contain declared attributes for " + managedType, managedType.getAttributes().containsAll(managedType.getDeclaredAttributes()));
            Assert.assertTrue("failed to lookup " + managedType + " by ordinal",getMetaModel().getType(managedType.getOrdinal())==managedType);
            if(managedType.isInstantiable()){
                final Object i = managedType.createInstance();
                final Proxy p = managedType.createProxy();
                Assert.assertEquals("createInstance().getClass() and getJavaType() return different classes for " + managedType, i.getClass(), managedType.getJavaType());
                Assert.assertTrue(p.getClass() + " is no subtype of " + managedType.getJavaType(), managedType.getJavaType().isAssignableFrom(p.getClass()));
            } else {
                boolean instanceCreationFailed=false, proxyCreationFailed=false;
                try{
                    managedType.createInstance();
                } catch(Exception ex){
                    instanceCreationFailed=true;
                }
                Assert.assertTrue(managedType + " is not instantiable yet createInstance() succeeded", instanceCreationFailed);
                try{
                    managedType.createProxy();
                } catch(Exception ex){
                    proxyCreationFailed=true;
                }
                Assert.assertTrue(managedType + " is not instantiable yet createProxy() succeeded", proxyCreationFailed);
            }
            
            final SortedSet<TypeES> superTypes = managedType.getSuperTypes();
            final Class javaType = managedType.getJavaType();
            for(TypeES superType : superTypes){
                final Class superJavaType = superType.getJavaType();
                Assert.assertTrue(managedType + " has an erroneous supertype " + superType,superJavaType.isAssignableFrom(javaType));
                Assert.assertTrue(superType + " of " + managedType + " is erroneous",superType.getSubTypes().contains(managedType));
            }
            
            final SortedSet<TypeES> subTypes = managedType.getSubTypes();
            for(TypeES subType : subTypes){
                Assert.assertTrue(javaType.isAssignableFrom(subType.getJavaType()));
                Assert.assertTrue(subType.getSuperTypes().contains(managedType));
            }
            
        }
    }

    
}
