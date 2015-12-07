package org.mousepilots.es.test.domain;

import javax.persistence.Entity;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 3-dec-2015
 */
@Entity
public class Account extends BaseEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}