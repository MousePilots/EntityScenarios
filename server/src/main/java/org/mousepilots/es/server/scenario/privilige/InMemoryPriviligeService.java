/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.Context;

/**
 * In-memory, non-blocking, thread-safe JVM-singleton implementation of {@link PriviligeService}, providing a {@link Builder} for the specification of privileges during deployment
 * of your application. This implementation supports adding only of {@link Privilige}s via its {@link Builder}. After invoking {@link #seal()}, it is sealed against modification.
 * Sealing the service is mandatory before invocation of its only service-method {@link #getPriviliges(java.lang.String, org.mousepilots.es.core.scenario.Context)}. This implementation is
 * suitable if your use case satisfies the following:
 * <ol>
 * <li>Each privilege definition adds one {@link Privilige} to a {@link HashSet}. Hence the number of privileges in your application should be less than, say, 10000.</li>
 * <li>Your application doesn't require addition or removal of privileges while deployed.</li>
 * </ol>
 * 
 * <p>
 * An example of the definition of a small set of privileges is given below.
 * <pre>
* {@code
*InMemoryPriviligeService
*   // privilige 1
*   .inScenario("createAccount")
*       .grant(CRUD.CREATE)
*       .on(Account_ES.TYPE,Account_ES.username,Account_ES.password,Account_ES.roles)
*       .toUsersInRoles("admin")
*       .commit()
*   // privilige 2
*   .inScenario("shop")
*       .grant(CRUD.READ)
*       .on(Product_ES.TYPE,(Set<AttributeES>) Product_ES.TYPE.getAttributes())
*       .commit()
*   // privilige 3
*   .inScenario("shop")
*       .grant(CRUD.CREATE)
*       .on(ShoppingCart_ES.TYPE,(Set<AttributeES>) ShoppingCart_ES.TYPE.getAttributes())
*       .toUsersInRoles("customer")
*       .commit()
*   //protect InMemoryPriviligeService against further modification 
*   .seal();
* } </pre>
* 
* Privilege 1 permits users having role {@code "admin"} to create an {@code Account}. Privilege 2 permits any user to read {@code Product}s. Privilege 3 permits customers to create a shopping cart.
* Because Privilige 2,3 share the scenario {@code "shop"}, customers can also read all {@code Product}s. The last line seals the {@link InMemoryPriviligeService} against further modifications.
* 
* The definition of privileges as demonstrated above is preferably done during application startup, e.g. in a {@link javax.servlet.ServletContextListener}. This ensures
* that privileges are properly replicated across instances in clustered setup.
* 
 * @author Jurjen van Geenen
 */
public class InMemoryPriviligeService extends AbstractPriviligeService{

    private static final InMemoryPriviligeService INSTANCE = new InMemoryPriviligeService();
    
    public static InMemoryPriviligeService getInstance() {
        return INSTANCE;
    }

    private InMemoryPriviligeService(){}
    
    private boolean sealed = false;
    
    private final AtomicLong idGenerator = new AtomicLong(0);
    
    private final Set<Privilige> priviliges = new HashSet<>();
    
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    private void assertSealed(boolean sealed){
        if(this.sealed!=sealed){
            throw new IllegalStateException(this + " is " + (this.sealed ? " not " : "") + "sealed against modification(s)");
        }
    }
    
    public synchronized InMemoryPriviligeService seal(){
        this.sealed = true;
        return this;
    }
    
    public boolean isSealed(){
        return this.sealed;
    }
    
    @Override
    protected Set<Privilige> collect(final String scenario, Set<CRUD> operations, Context context){
        assertSealed(true);
        final Predicate<Privilige> predicate = p   ->  
                p.getScenario().equals(scenario) && 
                operations.contains( p.getOperation() ) &&
                ( p.getUserNames().isEmpty() || p.getUserNames().contains(context.getUserName()) ) &&
                ( p.getRoleNames().isEmpty() || context.hasRoleIn( p.getRoleNames()) );
        final HashSet<Privilige> applicablePriviliges = 
                this.priviliges
                .stream()
                .filter(predicate)
                .collect(Collectors.toCollection(HashSet::new));
        return applicablePriviliges;
    }
    
    public class Builder{
        
        private static final String EITHER_USERS_OR_ROLES = "you should either specify users or roles but not both";
        

        private final String scenario;
        
        private CRUD operation;
        
        private ManagedTypeES type;
        
        private final Set<AttributeES> attributes = new HashSet<>();

        private final Set<String> userNames = new HashSet<>();
        
        private final Set<String> roleNames = new HashSet<>();
        

        private Builder(String scenario) {
            Objects.requireNonNull(scenario);
            assertSealed(false);
            this.scenario = scenario;
        }
        
        /**
         * Mandatory: specifies the operation to be granted. Your next call shall be either 
         * <ul>
         * <li>{@link #on(org.mousepilots.es.core.model.ManagedTypeES, org.mousepilots.es.core.model.AttributeES...)}, or,</li>
         * <li>{@link #on(org.mousepilots.es.core.model.ManagedTypeES, java.util.Collection)}</li>
         * </ul>
         * @param operation the operation to be granted
         * @return {@code this}
         */
        public Builder grant(CRUD operation){
            this.operation = operation;
            return this;
        }
        
        /**
         * Specifies the {@link ManagedTypeES} and {@link AttributeES}s on which the operation is to be allowed.<p/>
         * <strong>rule:</strong><p>{@code attributes} must be specified <em>if and only if</em> the operation being allowed is a {@link CRUD#CREATE}, {@link CRUD#READ} or {@link CRUD#UPDATE}.
         * Failing to observe this rule raises an {@link IllegalArgumentException}
         * @param managedType required
         * @param attributes attributes are mandatory for {@link CRUD#CREATE}, {@link CRUD#READ} or {@link CRUD#UPDATE} and prohibited for {@link CRUD#DELETE}
         * @throws IllegalArgumentException if you don't obey the <strong>rule</strong>
         * @return {@code this}
         */
        public Builder on(ManagedTypeES managedType, AttributeES... attributes) throws IllegalArgumentException{
            return on(managedType, Arrays.asList(attributes));
        }
        
        /**
         * Specifies the {@link ManagedTypeES} and {@link AttributeES}s on which the operation is to be allowed.<p/>
         * <strong>rule:</strong><p>{@code attributes} must be specified <em>if and only if</em> the operation being allowed is a {@link CRUD#CREATE}, {@link CRUD#READ} or {@link CRUD#UPDATE}.
         * Failing to observe this rule raises an {@link IllegalArgumentException}
         * @param managedType required
         * @param attributes attributes are mandatory for {@link CRUD#CREATE}, {@link CRUD#READ} or {@link CRUD#UPDATE} and prohibited for {@link CRUD#DELETE}
         * @throws IllegalArgumentException if you don't obey the <strong>rule</strong>
         * @return {@code this}
         */
        public Builder on(ManagedTypeES managedType, Collection<AttributeES> attributes) throws IllegalArgumentException{
            if(this.operation==CRUD.DELETE && attributes!=null && !attributes.isEmpty()){
                throw new IllegalArgumentException("you mustn't specify attributes for DELETE operations");
            }
            this.type = managedType;
            for(AttributeES attribute : attributes){
                if(type.getAttributes().contains(attribute)){
                    this.attributes.add(attribute);
                } else {
                    throw new IllegalArgumentException(attribute + " does not occur on " + type);
                }
            }
            return this;
        }
        
        private Builder addIfOtherIsEmpty(Set<String> toAddTo, Set<String> other, String errorMessage, String... values){
            if(other.isEmpty()){
                toAddTo.addAll(Arrays.asList(values));
            } else {
                throw new IllegalStateException(errorMessage);
            }
            return this;
        }
        
        /**
         * Optional: grants the privilege under construction only to the specified users.
         * @param userNames
         * @return {@code this}
         */
        public Builder toUsers(String... userNames){
            return addIfOtherIsEmpty(this.userNames, this.roleNames, EITHER_USERS_OR_ROLES, userNames);
        }

        /**
         * Optional: grants the privilege under construction only to users having at least one role in {@code roleNames}.
         * @param roleNames 
         * @return {@code this}
         */
        public Builder toUsersInRoles(String... roleNames){
            return addIfOtherIsEmpty(this.roleNames, this.userNames, EITHER_USERS_OR_ROLES, roleNames);        }
        
        /**
         * Stores the {@link Privilige} being built into the {@link InMemoryPriviligeService}.
         * @return 
         */
        public InMemoryPriviligeService commit(){
            synchronized(InMemoryPriviligeService.this){
                assertSealed(false);
                Privilige privilige = new Privilige();
                privilige.setScenario(scenario);
                privilige.setId(idGenerator.getAndIncrement());
                privilige.setOperation(operation);
                privilige.setType(type);
                privilige.setAttributes(attributes);
                privilige.setUserNames(userNames);
                privilige.setRoleNames(roleNames);
                validator.validate(privilige, Default.class);
                if(!InMemoryPriviligeService.this.priviliges.add(privilige)){
                    throw new IllegalStateException("duplicate privilige specification for scenario " + scenario + ":" + privilige);
                } 
                return InMemoryPriviligeService.this;
            }
        }
    }
    
    /**
     * Starts building a new {@link Privilige} for the specified {@code scenario}. You next call should be {@link Builder#grant(org.mousepilots.es.core.command.CRUD)}
     * @param scenario
     * @return the {@link Builder} for further specification of the {@link Privilige} to be granted
     */
    public static Builder inScenario(String scenario){
        Objects.requireNonNull(scenario, "scenario is required");
        return getInstance().new Builder(scenario);
    }
}
