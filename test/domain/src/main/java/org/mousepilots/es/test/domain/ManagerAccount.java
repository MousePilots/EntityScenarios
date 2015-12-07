package org.mousepilots.es.test.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 3-dec-2015
 */
@Entity
public class ManagerAccount extends Account{

    @OneToMany
    private List<User> subordinates;

    public List<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
    }




}