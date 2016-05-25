/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.shared;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.Assert;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.AttributeVisitor;
import org.mousepilots.es.core.model.BasicTypeES;
import org.mousepilots.es.core.model.CollectionAttributeES;
import org.mousepilots.es.core.model.EmbeddableTypeES;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.EntityTypeES;
import org.mousepilots.es.core.model.ListAttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.MapAttributeES;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.PluralAttributeES;
import org.mousepilots.es.core.model.SetAttributeES;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.TypeVisitor;
import org.mousepilots.es.core.model.impl.EntityManagerImpl;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.proxy.Proxy;
import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.core.scenario.priviliges.Priviliges;
import org.mousepilots.es.server.scenario.privilige.InMemoryPriviligeService;
import org.mousepilots.es.server.scenario.privilige.ProxyCreator;
import org.mousepilots.es.test.domain.Gender;
import org.mousepilots.es.test.domain.embeddables.Address;
import org.mousepilots.es.test.domain.embeddables.Address_ES;
import org.mousepilots.es.test.domain.embeddables.PhoneType;
import org.mousepilots.es.test.domain.embeddables.PhoneType_ES;
import org.mousepilots.es.test.domain.entities.BasicMap;
import org.mousepilots.es.test.domain.entities.BasicMap_ES;
import org.mousepilots.es.test.domain.entities.EmbeddableMap;
import org.mousepilots.es.test.domain.entities.EmbeddableMap_ES;
import org.mousepilots.es.test.domain.entities.Employee;
import org.mousepilots.es.test.domain.entities.Employee_ES;
import org.mousepilots.es.test.domain.entities.EntityMap;
import org.mousepilots.es.test.domain.entities.EntityMap_ES;
import org.mousepilots.es.test.domain.entities.Manager;
import org.mousepilots.es.test.domain.entities.ManagerAccount;
import org.mousepilots.es.test.domain.entities.ManagerAccount_ES;
import org.mousepilots.es.test.domain.entities.Manager_ES;
import org.mousepilots.es.test.domain.entities.Phone;
import org.mousepilots.es.test.domain.entities.Phone_ES;
import org.mousepilots.es.test.domain.entities.Role;
import org.mousepilots.es.test.domain.entities.Role_ES;
import org.mousepilots.es.test.domain.entities.User;
import org.mousepilots.es.test.domain.entities.User_;
import org.mousepilots.es.test.domain.entities.User_ES;
import org.mousepilots.es.test.domain.entities.WorkEnvironment;
import org.mousepilots.es.test.domain.entities.WorkEnvironment_ES;
import org.mousepilots.es.test.server.ScenarioServiceBean;
import org.mousepilots.es.test.server.domain.mmx.JPA;

/**
 *
 * @author AP34WV
 */
public class TestDomain extends AbstractTest {
    
    private static boolean isNullOrEmpty(Collection c){
        return c==null || c.isEmpty();
    }
    
    private static boolean isNullOrEmpty(Map m){
        return m==null || m.isEmpty();
    }
    
    private static class Pair {

        private final Object a, b;

        private Pair(Object a, Object b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + java.util.Objects.hashCode(this.a);
            hash = 23 * hash + java.util.Objects.hashCode(this.b);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair other = (Pair) obj;
            if (!java.util.Objects.equals(this.a, other.a)) {
                return false;
            }
            if (!java.util.Objects.equals(this.b, other.b)) {
                return false;
            }
            return true;
        }
        
        boolean isNullEqual(){
            return (a==null) == (b==null);
        }
        
        boolean isNull(){
            return a==null && b==null;
        }
    }

    private final ScenarioServiceBean scenarioServiceBean = new ScenarioServiceBean();

    public TestDomain() {
        super(TestDomain.class);
    }

    private final Set<Pair> visitedByTypeValueComparator = new HashSet();
    
    private final TypeVisitor<Boolean, Pair> typeValueComparator = new TypeVisitor<Boolean, Pair>() {


        @Override
        public Boolean visit(BasicTypeES t, Pair p) {
            return Objects.equals(p.a, p.b);
        }

        private boolean visitManagedType(ManagedTypeES t, Pair p) {
            
            if(!p.isNullEqual()){
                return false;
            }
            
            if(p.isNull()){
                return true;
            }
            
            if (visitedByTypeValueComparator.contains(p)) {
                return true;
            } else {
                visitedByTypeValueComparator.add(p);
                final Set<AttributeES> attributes = t.getAttributes();
                for (AttributeES a : attributes) {
                    final MemberES javaMember = a.getJavaMember();
                    final Pair attributeValuePair = new Pair(javaMember.get(p.a), javaMember.get(p.b));
                    final boolean same = (Boolean) a.accept(attributeValueComparator, attributeValuePair);
                    if (!same) {
                        return false;
                    }
                }
                return true;
            }

        }

        @Override
        public Boolean visit(EmbeddableTypeES t, Pair p) {
            return visitManagedType(t, p);
        }

        @Override
        public Boolean visit(MappedSuperclassTypeES t, Pair p) {
            return visitManagedType(t, p);
        }

        @Override
        public Boolean visit(EntityTypeES t, Pair p) {
            return visitManagedType(t, p);
        }
    };

    private final AttributeVisitor<Boolean, Pair> attributeValueComparator = new AttributeVisitor<Boolean, Pair>() {
        @Override
        public Boolean visit(SingularAttributeES a, Pair p) {
            if (a.isId() || a.isVersion()) {
                return true;
            } else {
                return (Boolean) a.getType().accept(typeValueComparator, p);
            }
        }
        
        private boolean visitJavaUtilCollection(PluralAttributeES a, Pair p){
            final Collection va = (Collection) p.a,vb= (Collection) p.b;
            if(isNullOrEmpty(va)==isNullOrEmpty(vb)){
                if(isNullOrEmpty(va) && isNullOrEmpty(vb)){
                    return true;
                } else {
                    return va.size()==vb.size();
                }
            } else {
                return false;
            }
        }

        @Override
        public Boolean visit(CollectionAttributeES a, Pair p) {
            return visitJavaUtilCollection(a, p);
        }

        @Override
        public Boolean visit(ListAttributeES a, Pair p) {
            return visitJavaUtilCollection(a, p);
        }

        @Override
        public Boolean visit(SetAttributeES a, Pair p) {
            return visitJavaUtilCollection(a, p);
        }

        @Override
        public Boolean visit(MapAttributeES a, Pair p) {
            Map va = (Map) p.a, vb = (Map) p.b;
            if(isNullOrEmpty(va)==isNullOrEmpty(vb)){
                if(isNullOrEmpty(va) && isNullOrEmpty(vb)){
                    return true;
                } else {
                    return va.size()==vb.size();
                }
            } else {
                return false;
            }
        }
    };
    
    private Address createAddress(final EntityManagerImpl entityManagerES, int i) {
        final Address a = entityManagerES.create(Address_ES.__TYPE);
        a.setCity("city " + i);
        a.setCountry("country " + i);
        a.setHouseNumber(String.valueOf(i));
        a.setStreet("street " + i);
        a.setZipCode("zipcode " + i);
        return a;
    }
    
    private Phone createPhone(final EntityManagerImpl entityManagerES, User owner, int i) {
        final Phone p = entityManagerES.create(Phone_ES.__TYPE);
        p.setPhoneNumber(String.valueOf(i));
        p.setOwner(owner);
        return p;
    }
    

    private void doTestCreates() {
        visitedByTypeValueComparator.clear();
        final EntityManagerImpl entityManagerES = (EntityManagerImpl) JPA.createEntityManagerES();
        final Role manager = entityManagerES.create(Role_ES.__TYPE);
        manager.setName("manager");
        final Role employee = entityManagerES.create(Role_ES.__TYPE);
        employee.setName("employee");

        final Employee john = entityManagerES.create(Employee_ES.__TYPE);
        john.setAge(21);
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setUserName("john");
        john.setGender(Gender.MALE);
        employee.getUsers().add(john);
        john.getRoles().add(employee);

        final Manager bill = entityManagerES.create(Manager_ES.__TYPE);
        bill.getSubordinates().add(john);
        john.setManager(bill);
        bill.setUserName("bill");
        bill.setAge(40);
        bill.setGender(Gender.MALE);
        bill.getEmailAddresses().addAll(Arrays.asList("bill@gates.org", "elon@musk.org"));
        bill.setFirstName("Bill");
        bill.setLastName("Gates");
        bill.getRoles().addAll(Arrays.asList(manager, employee));
        manager.getUsers().add(bill);
        employee.getUsers().add(bill);

        WorkEnvironment hq = entityManagerES.create(WorkEnvironment_ES.__TYPE);
        hq.setOfficeLocation("Redmond");
        hq.setOfficeNumber(String.valueOf("1, top floor"));
        bill.setWorkEnvironment(hq);

        final ManagerAccount account = entityManagerES.create(ManagerAccount_ES.__TYPE);
        account.setDescription("Bill's account");
        bill.setAccount(account);

        for (int i = 0; i < 5; i++) {
            Address a = createAddress(entityManagerES, i);
            if (i < 4) {
                bill.getAddresses().add(a);
            } else {
                bill.setManagerAddress(a);
            }
        }
        
        final BasicMap basicMap = entityManagerES.create(BasicMap_ES.__TYPE);
        for(int i=0;i<5;i++){
            basicMap.getBasicBasic().put("key" + i, "value" + i);
        }
        for(int i=0;i<5;i++){
            Address a = createAddress(entityManagerES, i);
            basicMap.getBasicEmbeddable().put("key" + i,a);
        }
        for(int i=0;i<5;i++){
            final Phone p = createPhone(entityManagerES, i%2==0 ? john : bill, i);
            basicMap.getBasicEntity().put("key"+i, p);
        }
        
        final EmbeddableMap embeddableMap = entityManagerES.create(EmbeddableMap_ES.__TYPE);
        for(int i=0; i<5; i++){
            PhoneType phoneType = entityManagerES.create(PhoneType_ES.__TYPE);
            phoneType.setType("type" + i);
            embeddableMap.getEmbeddableBasic().put(phoneType, "value" + i);
        }
        for(int i=0; i<5; i++){
            PhoneType phoneType = entityManagerES.create(PhoneType_ES.__TYPE);
            phoneType.setType("type" + i);
            final Address address = createAddress(entityManagerES, i);
            embeddableMap.getEmbeddableEmbeddable().put(phoneType, address);
        }

        for(int i=0; i<5; i++){
            PhoneType phoneType = entityManagerES.create(PhoneType_ES.__TYPE);
            phoneType.setType("type" + i);
            final Phone phone = entityManagerES.create(Phone_ES.__TYPE);
            phone.setOwner(i%2==0 ? john : bill);
            phone.setPhoneNumber(String.valueOf(i));
            embeddableMap.getEmbeddableEntity().put(phoneType, phone);
        }
        
        final EntityMap entityMap = entityManagerES.create(EntityMap_ES.__TYPE);
        for(int i=0; i<5; i++){
            final Phone phone = entityManagerES.create(Phone_ES.__TYPE);
            phone.setOwner(i%2==0 ? john : bill);
            phone.setPhoneNumber(String.valueOf(i));
            entityMap.getEntityBasic().put(phone, String.valueOf(i));
            
            PhoneType phoneType = entityManagerES.create(PhoneType_ES.__TYPE);
            phoneType.setType("type" + i);
            entityMap.getEntityEmbeddable().put(phone, createAddress(entityManagerES, i));
            entityMap.getEntityEntity().put(phone, phone);
        }
        
        
        
        final List<Command> commands = entityManagerES.getTransaction().getCommands();
        this.scenarioServiceBean.submit(commands);
        for (Command command : commands) {
            final Proxy proxy = command.getProxy();
            final Object subject = command.getRealSubject();
            final ManagedTypeESImpl type = proxy.__getProxyAspect().getType();
            Pair pair = new Pair(proxy,subject);
            final Boolean equal = (Boolean) type.accept(typeValueComparator, pair);
            assertTrue(proxy + " and " + subject + " are not equal w.r.t. their basic-typed singular attributes", equal);
        }
        //this.entityCreations.addAll(commands.stream().filter(c -> c instanceof CreateEntity).collect(Collectors.toList()));
    }
    
    private <U extends User> U findUser(EntityManager em, Class<U> userClass, String userName){
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<U> cq = cb.createQuery(userClass);
        final Root<U> u = cq.from(userClass);
        cq.where(cb.equal(u.get(User_.userName),userName)).select(u);
        return  em.createQuery(cq).getSingleResult();
    }
    
    private void doTestUpdates(){
        visitedByTypeValueComparator.clear();
        final InMemoryPriviligeService priviligeService = InMemoryPriviligeService.getInstance();
        final Set<ManagedTypeES<?>> managedTypes = (Set) getMetaModel().getManagedTypes();
        final String scenario = "doTestUpdates";
        for(ManagedTypeES type : managedTypes){
            priviligeService.inScenario(scenario).grant(CRUD.READ).on(type, type.getAttributes()).commit();
        }
        EntityManager em = JPA.createEntityManager();
        final Priviliges priviliges = priviligeService.getPriviliges(scenario, new Context(){
            @Override
            public String getUserName() {
                return "bill"; //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Object getEntityManager() {
                return null;
            }
            
        });
        ProxyCreator proxyCreator = new ProxyCreator(priviliges);
        Employee john = findUser(em, Employee.class, "john");
        Manager  bill = findUser(em, Manager.class, "bill");
        final Proxy<Manager>  billProxy = proxyCreator.getProxyFor(bill);
        final Proxy<Employee> johnProxy = proxyCreator.getProxyFor(john);
        em.close();
        
        
        Assert.assertTrue(billProxy.__subject().getSubordinates().contains(johnProxy.__subject()));
        Assert.assertTrue(johnProxy.__subject().getManager()==billProxy);
        final EntityManagerImpl entityManagerES = (EntityManagerImpl) createEntityManager();
        entityManagerES.manageAll(Arrays.asList(billProxy,johnProxy));
        final List<User> allUsers = entityManagerES.select(User_ES.__TYPE, u->true, (u1,u2)->u1.getUserName().compareTo(u2.getUserName()));
        Assert.assertTrue(allUsers.contains(billProxy));
        Assert.assertTrue(allUsers.contains(johnProxy));
        Assert.assertTrue(entityManagerES.select(User.class, u-> "bill".equals(u.getUserName()), null).size()==1);
        Assert.assertTrue(entityManagerES.select(User.class, u-> "john".equals(u.getUserName()), null).size()==1);
        final Manager billSubject = billProxy.__subject();
        
        
        billSubject.setAge(65);
        billSubject.getEmailAddresses().clear();
        billSubject.getAccount().setDescription("updated description");
        final String updated_country = "updated country";
        billSubject.getAddresses().get(0).setCountry(updated_country);
        final List<Command> commands = entityManagerES.getTransaction().getCommands();
        this.scenarioServiceBean.submit(commands);
        
        em = JPA.createEntityManager();
        bill = findUser(em, Manager.class, "bill");
        Assert.assertTrue(bill.getAge()==65);
        Assert.assertTrue(bill.getEmailAddresses()==null || bill.getEmailAddresses().isEmpty());
        Assert.assertTrue(bill.getAccount().getDescription().equals("updated description"));
        Assert.assertTrue(bill.getAddresses().stream().anyMatch(a -> updated_country.equals(a.getCountry())));

        
        
    }
    
    public void testAll(){
        doTestCreates();
        doTestUpdates();
    }

}
