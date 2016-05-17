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
import java.util.concurrent.ConcurrentSkipListSet;
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
import org.mousepilots.es.core.scenario.priviliges.PriviligeService;
import org.mousepilots.es.core.util.PropertyUtils;
import org.mousepilots.es.core.util.StringUtils;

/**
 * In-memory, non-blocking, JVM-singleton implementation of {@link PriviligeService}, providing a {@link Builder} for the specification of privileges during deployment
 * of your application. This implementation is
 * suitable if your use case satisfies the following:
 * <ol>
 * <li>Each privilege definition adds one {@link Privilige} to a {@link Set}. Hence the number of privileges in your application should be reasonable.</li>
 * <li>Your application doesn't require addition or removal of privileges while deployed.</li>
 * </ol>
 * 
 * <p>
 * An example of the definition of a small set of privileges is given below. Note that specifying usernames ({@link Builder#toUsers(java.lang.String...) }) is optional, as is specifying
 * role names ({@link Builder#toUsersInRoles(java.lang.String...)}).
 * <pre>
* {@code
*InMemoryPriviligeService.getInstance()
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
    
    private final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    
    public static InMemoryPriviligeService getInstance() {
        return INSTANCE;
    }

    private final AtomicLong privilegeIdGenerator = new AtomicLong(0);
    
    private final Set<Privilige> priviliges = new ConcurrentSkipListSet<>( (p0,p1) -> p0.getId().compareTo(p1.getId()) );
    
    
    protected InMemoryPriviligeService(){}
    
    @Override
    protected Set<Privilige> collect(final String scenario, Set<CRUD> operations, Context context){
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
        
        private final String scenario;
        
        private CRUD operation;
        
        private ManagedTypeES type;
        
        private final Set<AttributeES> attributes = new HashSet<>();

        private final Set<String> userNames = new HashSet<>();
        
        private final Set<String> roleNames = new HashSet<>();

        private Builder(String scenario) {
            Objects.requireNonNull(scenario,"scenario is required");
            this.scenario = scenario;
        }
        
        /**
         * Mandatory: sets (or overwrites) the operation to be granted. Your next call shall be either 
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

        
        /**
         * Optional: grants the privilege under construction only to the specified users.
         * @param userNames
         * @return {@code this}
         */
        public Builder toUsers(String... userNames){
            this.userNames.addAll(Arrays.asList(userNames));
            return this;
        }

        /**
         * Optional: grants the privilege under construction only to users having at least one role in {@code roleNames}.
         * @param roleNames 
         * @return {@code this}
         */
        public Builder toUsersInRoles(String... roleNames){
            this.roleNames.addAll(Arrays.asList(roleNames));
            return this;
        }
        
        /**
         * Stores the {@link Privilige} being built into the {@link InMemoryPriviligeService}.
         * @return 
         */
        public InMemoryPriviligeService commit(){
            Privilige p0 = new Privilige();
            p0.setScenario(scenario);
            p0.setId(privilegeIdGenerator.getAndIncrement());
            p0.setOperation(operation);
            p0.setType(type);
            p0.setAttributes(attributes);
            p0.setUserNames(userNames);
            p0.setRoleNames(roleNames);
            VALIDATOR.validate(p0, Default.class);
            InMemoryPriviligeService.this.priviliges.forEach(
                p1 ->   
                PropertyUtils.assertNotEquals(
                    p0, 
                    p1, 
                    () -> new IllegalStateException("duplicate privilige " + p0), 
                    Privilige::getScenario, 
                    Privilige::getOperation, 
                    Privilige::getTypeOrdinal, 
                    Privilige::getAttributeOrdinals, 
                    Privilige::getUserNames, 
                    Privilige::getRoleNames
                )
            );
            InMemoryPriviligeService.this.priviliges.add(p0);
            return InMemoryPriviligeService.this;
        }
    }
    
    /**
     * Starts building a new {@link Privilige} for the specified {@code scenario}. You next call should be {@link Builder#grant(org.mousepilots.es.core.command.CRUD)}
     * @param scenario
     * @return the {@link Builder} for further specification of the {@link Privilige} to be granted
     */
    public Builder inScenario(String scenario){
        StringUtils.requireNullOrEmpty(scenario, "scenario must not be null or empty");
        return new Builder(scenario);
    }
}
