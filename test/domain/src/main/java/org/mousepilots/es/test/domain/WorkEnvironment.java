package org.mousepilots.es.test.domain;

import javax.persistence.Entity;

/**
 * Entity modeling a work environment.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
public class WorkEnvironment extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String officeNumber;
    private String officeLocation;

    public WorkEnvironment() {
    }

    public WorkEnvironment(String officeNumber, String officeLocation) {
        this.officeNumber = officeNumber;
        this.officeLocation = officeLocation;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    @Override
    public int hashCode() {
        int hash = 99;
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