package org.mousepilots.es.test.domain.entities;

import org.mousepilots.es.test.domain.BaseEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.mousepilots.es.test.domain.embeddables.Address;
import org.mousepilots.es.test.domain.Person;

/**
 * Entity testing all the collection types and arities.
 *
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
@Table(name = "Users")
public abstract class User<A extends Account> extends Person {

    private static final long serialVersionUID = 1L;
    private String userName;

    @OneToOne(targetEntity = Account.class)
    private A account;

    @ElementCollection
    private List<Address> addresses;

    @ElementCollection
    private List<String> emailAddresses;

    @OneToMany(targetEntity = User.class)
    private Set<User> subordinates;

    @OneToOne
    private WorkEnvironment workEnvironment;

    @ManyToMany
    @JoinTable(
        name = "USER_ROLE",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    )
    private Collection<Role> roles;

    @JoinTable(
        name = "USER_PREVIOUSROLE",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    )
    @OneToMany
    private Collection<Role> previousRoles;

    public User() {
    }

    public A getAccount() {
        return account;
    }

    public void setAccount(A account) {
        this.account = account;
    }

    @ProvidesOwners
    public Set<String> getOwners() {
        return Collections.singleton(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public WorkEnvironment getWorkEnvironment() {
        return workEnvironment;
    }

    public void setWorkEnvironment(WorkEnvironment workEnvironment) {
        this.workEnvironment = workEnvironment;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<Role> getPreviousRoles() {
        return previousRoles;
    }

    public void setPreviousRoles(Collection<Role> previousRoles) {
        this.previousRoles = previousRoles;
    }

    public Set<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<User> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public int hashCode() {
        int hash = 19;
        hash += this.getId() != null ? this.getId().hashCode() : 0;
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
        BaseEntity other = (BaseEntity) obj;
        if (!this.getId().equals(other.getId()) && (this.getId() == null)
                || !this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }
}
