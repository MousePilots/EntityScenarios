/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.core.scenario.priviliges.Priviliges;
import org.mousepilots.es.test.shared.AbstractTest;

/**
 *
 * @author ap34wv
 */
public class InMemoryPriviligeServiceTest extends AbstractTest{
    
    private static List<String> nOf(String prefix, int n ){
        final List retval = new ArrayList<>(n);
        for(int i=0;i<n;i++){
            retval.add(prefix + "n");
        }
        return retval;
    }
    
    private static <T> List<T> nOf(Function<Integer,T> producer, int n){
        final List<T> retval = new ArrayList<>(n);
        for(int i=0;i<n;i++){
            retval.add(producer.apply(i));
        }
        return retval;
    }
    
    private static <T> List<List<T>> subListsOf(List<T> l){
        final List<List<T>> retval = new LinkedList<>();
        for(int size = 1; size<=l.size(); size++){
            for(int startPosition = 0; startPosition + size <= l.size(); startPosition++){
                retval.add(l.subList(startPosition, startPosition + size));
            }
        }
        return retval;
    }
    
    private static final List<String> SCENARIOS = nOf("scenario", 2);
    private static final List<String> USERS = nOf("user", 3);
    private static final List<String> ROLES = nOf("role", 5);
    private static final List<List<String>> ROLE_LISTS = subListsOf(ROLES);

    private static class TestContext implements Context{
        
        private final int i;

        private TestContext(int i) {
            this.i = i;
        }

        @Override
        public String getUserName() {
            return USERS.get(i % USERS.size());
        }
        
        public List<String> getRoles(){
            return ROLE_LISTS.get( (7*i) % ROLE_LISTS.size() );
        }

        @Override
        public boolean isUserInRole(String role) {
            return getRoles().contains(role);
        }

        @Override
        public Object getEntityManager() {
            return null;
        }
    }
    
    private static final List<TestContext> CONTEXTS = nOf( i -> new TestContext(i), USERS.size());
    
    public InMemoryPriviligeServiceTest() {
        super(InMemoryPriviligeServiceTest.class);
    }

    public void testGrantOnAllTypes(){
//        for(String scn)
//        for(CRUD o1 : CRUD.values()){
//            for(ManagedTypeESImpl t1 : getManagedTypes()){
//                
//                final InMemoryPriviligeService inMemoryPriviligeService = new InMemoryPriviligeService();
//                final Set<AttributeES> attributes1 = t1.getAttributes();
//                switch(o1){
//                    case DELETE : inMemoryPriviligeService.inScenario(SCENARIO_1).grant(o1).on(t1).commit(); break;
//                    default     : inMemoryPriviligeService.inScenario(SCENARIO_1).grant(o1).on(t1,attributes1).commit(); break;
//                }
//                final Priviliges priviliges = inMemoryPriviligeService.getPriviliges(SCENARIO_1, serverContext);
//                for(CRUD o2 : CRUD.values()){
//                    for(ManagedTypeESImpl t2 : getManagedTypes()){
//                        final SortedSet<AttributeES> attributes2 = t2.getAttributes();
//                        for(AttributeES attribute2 : attributes2){
//                            assertTrue(
//                                priviliges.isAllowed(o2, t2, attribute2) == (o1==o2 && t1==t2)
//                            );
//                        }
//                    }
//                }
//            }
//        
//        }
    }
    
    
    
}
