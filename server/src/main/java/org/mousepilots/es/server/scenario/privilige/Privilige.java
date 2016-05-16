/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;

@Entity
public class Privilige implements Serializable{
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull(message = "scenario is required")
    private String scenario;
    
    @NotNull(message = "operation is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "crud")
    private CRUD operation;
    
    @NotNull(message = "type is required")
    private Integer typeOrdinal;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> attributeOrdinals = new HashSet<>();
    
    /**
     * usernames can be checked in query
     */
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> userNames = new HashSet<>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roleNames = new HashSet<>();
    

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public CRUD getOperation() {
        return operation;
    }

    public void setOperation(CRUD operation) {
        this.operation = operation;
    }
    
    protected Integer getTypeOrdinal() {
        return typeOrdinal;
    }

    protected void setTypeOrdinal(Integer typeOrdinal) {
        this.typeOrdinal = typeOrdinal;
    }
    
    public ManagedTypeES getType(){
        return typeOrdinal==null ? null : (ManagedTypeES) AbstractMetamodelES.getInstance().getType(typeOrdinal);
    }
    
    public void setType(ManagedTypeES type){
        this.typeOrdinal=type.getOrdinal();
    }

    protected Set<Integer> getAttributeOrdinals() {
        return attributeOrdinals;
    }

    protected void setAttributeOrdinals(Set<Integer> attributeOrdinals) {
        this.attributeOrdinals = attributeOrdinals;
    }
    
    public Set<AttributeES> getAttributes(){
        final AbstractMetamodelES metamodel = AbstractMetamodelES.getInstance();
        Set<AttributeES> retval = new HashSet<>();
        attributeOrdinals.forEach( o -> retval.add(metamodel.getAttribute(o)));
        return retval;
    }
    
    public void setAttributes(Set<AttributeES> attributes){
        attributeOrdinals.clear();
        attributes.forEach(a -> attributeOrdinals.add(a.getOrdinal()));
    }
    
    public Set<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(Set<String> userNames) {
        this.userNames = userNames;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Privilige other = (Privilige) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
