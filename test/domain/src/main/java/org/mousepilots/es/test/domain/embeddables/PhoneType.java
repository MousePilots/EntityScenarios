package org.mousepilots.es.test.domain.embeddables;

import java.io.Serializable;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.type);
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
        final PhoneType other = (PhoneType) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
}