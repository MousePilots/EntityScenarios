package org.mousepilots.es.test.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity testing all the collection types and arities.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
@Table(name = "Users")
public abstract class User<A extends Account> extends Person {
    private static final long serialVersionUID = 1L;
    private String userName;

    @OneToOne
    private A account;

    @ElementCollection
    private List<Address> addresses;
    @ElementCollection
    private List<String> emailAddresses;
    @OneToMany(targetEntity = User.class)
    private Set<User> subordinates;
    @OneToOne
    private WorkEnvironment workEnvironment;
    @ManyToMany(targetEntity = Role.class)
    private Collection<Role> roles;

    @ManyToMany
    private Collection<Role> previousRoles;

    public User() {
    }

    public User(String userName, List<Address> addresses,
            List<String> emailAddresses, Set<User> subordinates,
            WorkEnvironment workEnvironment, Collection<Role> roles,
            String firstName, String infix, String lastName, int age, String sex) {
        super(firstName, infix, lastName, age, sex);
        this.userName = userName;
        this.addresses = addresses;
        this.emailAddresses = emailAddresses;
        this.subordinates = subordinates;
        this.workEnvironment = workEnvironment;
        this.roles = roles;
    }

    public A getAccount() {
        return account;
    }

    public void setAccount(A account) {
        this.account = account;
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
                || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}