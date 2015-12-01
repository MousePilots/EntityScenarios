package org.mousepilots.es.test.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity that models a role.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
@Table(name = "Roles")
public class Role extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String name, Collection<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}