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
public class User extends Person {
    private static final long serialVersionUID = 1L;
    private String userName;
    @ElementCollection
    private List<Address> addresses;
    @ElementCollection
    private List<String> emailAddresses;
    @OneToMany(targetEntity = User.class)
    private Set<User> subordinates;
    @OneToOne
    private WorkEnvironment workEnvironment;
    @ManyToMany(mappedBy = "users", targetEntity = Role.class)
    private Collection<Role> roles;

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

    public Set<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<User> subordinates) {
        this.subordinates = subordinates;
    }
}