package org.mousepilots.es.test.domain.entities;

import org.mousepilots.es.test.domain.BaseEntity;
import java.util.Objects;
import javax.persistence.Entity;

/**
 * @author Nicky Ernste
 * @version 1.0, 9-12-2015
 */
@Entity
public class Account extends BaseEntity {

    private String description;

    public Account() {
    }

    public Account(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.description);
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
        final Account other = (Account) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
}