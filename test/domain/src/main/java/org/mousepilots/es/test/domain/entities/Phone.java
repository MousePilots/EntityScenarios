package org.mousepilots.es.test.domain.entities;

import org.mousepilots.es.test.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity modeling a phone.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
public class Phone extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String phoneNumber;
    @ManyToOne
    private User owner;

    public Phone() {
    }

    public Phone(String phoneNumber, User owner) {
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 33;
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