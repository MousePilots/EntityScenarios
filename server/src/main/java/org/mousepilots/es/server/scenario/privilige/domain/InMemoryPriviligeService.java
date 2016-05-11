/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige.domain;

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
 * In-memory implementation of {@link PriviligeService}, providing a {@link Builder} for the specification of privileges.
 * @author ap34wv
 */
public class InMemoryPriviligeService extends AbstractPriviligeService{

    private static final InMemoryPriviligeService INSTANCE = new InMemoryPriviligeService();
    
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    public static InMemoryPriviligeService getInstance() {
        return INSTANCE;
    }
    
    private InMemoryPriviligeService(){}
    
    private final AtomicLong idGenerator = new AtomicLong(0);
    
    private final Set<Privilige> priviliges = new HashSet<>();
    
    protected InMemoryPriviligeService addPrivilige(Privilige privilige){
            if(priviliges.add(privilige)){
                throw new IllegalStateException("duplicate privilige specification :" + privilige);
            } else return this;
        
    }

    @Override
    protected Set<Privilige> getPriviliges(final String scenario, Set<CRUD> operations, Context context){
        final Predicate<Privilige> predicate = p   ->  
                p.getScenario().equals(scenario) && 
                operations.contains( p.getOperation() ) &&
                ( p.getUserNames().isEmpty() || p.getUserNames().contains(context.getUserName()) ) &&
                ( p.getRoleNames().isEmpty() || context.hasRoleIn(p.getRoleNames()) );
        return this.priviliges.stream().filter(predicate).collect(Collectors.toCollection(HashSet::new));
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
            this.scenario = scenario;
        }
        
        /**
         * Mandatory: specifies the operation to be granted
         * @param operation
         * @return 
         */
        public Builder grant(CRUD operation){
            this.operation = operation;
            return this;
        }
        
        /**
         * 
         * @param managedType required
         * @param attributes attributes are required except for {@link CRUD#DELETE}s
         * @return {@code this}
         */
        public Builder on(ManagedTypeES managedType, AttributeES... attributes){
            return on(managedType, Arrays.asList(attributes));
        }
        
        public Builder on(ManagedTypeES managedType, Collection<AttributeES> attributes){
            if(this.operation==CRUD.DELETE && !attributes.isEmpty()){
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
        
        private Builder addIfOtherIsEmpty(Set<String> toAddTo, Set<String> emptySet, String errorMessage, String... values){
            if(emptySet.isEmpty()){
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
        
        public InMemoryPriviligeService ok(){
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
    
    public static Builder inScenario(String scenario){
        Objects.requireNonNull(scenario, "scenario is required");
        return getInstance().new Builder(scenario);
    }
}
