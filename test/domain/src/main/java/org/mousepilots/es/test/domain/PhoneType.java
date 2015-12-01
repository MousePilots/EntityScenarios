package org.mousepilots.es.test.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Embeddable that models a phone type.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Embeddable
public class PhoneType implements Serializable{
    private String type;

    public PhoneType() {
    }

    public PhoneType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}