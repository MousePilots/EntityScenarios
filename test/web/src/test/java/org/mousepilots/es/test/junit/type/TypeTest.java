/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.junit.type;

import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import junit.framework.Assert;
import org.junit.Test;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.test.junit.AbstractTest;

/**
 *
 * @author geenenju
 */
public class TypeTest extends AbstractTest{
    
    @Test
    public void testEntities(){
        final Set<EntityTypeESImpl> entityTypes = (Set) getMetaModel().getEntities();
        getLogger().log(Level.INFO, "testing {0} entity types", entityTypes.size());
        for(EntityTypeESImpl et : entityTypes){
            Assert.assertTrue("attributes must contain declared attributes for " + et, et.getAttributes().containsAll(et.getDeclaredAttributes()));
            Assert.assertTrue("failed to lookup " + et + " by ordinal",getMetaModel().getType(et.getOrdinal())==et);
            if(et.isInstantiable()){
                final Object i = et.createInstance();
                final Proxy p = et.createProxy();
                Assert.assertEquals(i.getClass(), et.getJavaType());
                Assert.assertTrue(et.getJavaType().isAssignableFrom(p.getClass()));
            } else {
                boolean instanceCreationFailed=false, proxyCreationFailed=false;
                try{
                    et.createInstance();
                } catch(Exception ex){
                    instanceCreationFailed=true;
                }
                try{
                    et.createProxy();
                } catch(Exception ex){
                    proxyCreationFailed=true;
                }
                Assert.assertTrue(instanceCreationFailed);
                Assert.assertTrue(proxyCreationFailed);
            }
            
            final SortedSet<TypeES> superTypes = et.getSuperTypes();
            final Class javaType = et.getJavaType();
            for(TypeES st : superTypes){
                final Class superJavaType = st.getJavaType();
                Assert.assertTrue(superJavaType.isAssignableFrom(javaType));
            }
            
            final SortedSet<TypeES> subTypes = et.getSubTypes();
            for(TypeES st : subTypes){
                Assert.assertTrue(javaType.isAssignableFrom(st.getJavaType()));
            }
            
        }
    }

    
}
