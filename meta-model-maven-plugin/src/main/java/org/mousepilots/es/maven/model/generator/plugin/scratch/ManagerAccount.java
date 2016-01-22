package org.mousepilots.es.maven.model.generator.plugin.scratch;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
@Entity
public class ManagerAccount extends Account{

    @OneToMany
    private List<User> subordinates;

    public ManagerAccount() {
    }

    public ManagerAccount(List<User> subordinates) {
        this.subordinates = subordinates;
    }

    public List<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.subordinates);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ManagerAccount other = (ManagerAccount) obj;
        if (!Objects.equals(this.subordinates, other.subordinates)) {
            return false;
        }
        return true;
    }
}