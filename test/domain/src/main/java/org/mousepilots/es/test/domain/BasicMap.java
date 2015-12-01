package org.mousepilots.es.test.domain;

import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;

/**
 * Entity that tests Maps with basic keys.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
public class BasicMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ElementCollection(targetClass = String.class)
    @MapKeyClass(String.class)
    private Map<String, String> basicBasic;

    @ElementCollection(targetClass = Address.class)
    @MapKeyClass(String.class)
    private Map<String, Address> basicEmbeddable;

    @ManyToMany(targetEntity = Phone.class)
    @MapKeyClass(String.class)
    private Map<String, Phone> basicEntity;

    public BasicMap() {
    }

    public BasicMap(Map<String, String> basicBasic, Map<String,
            Address> basicEmbeddable, Map<String, Phone> basicEntity) {
        this.basicBasic = basicBasic;
        this.basicEmbeddable = basicEmbeddable;
        this.basicEntity = basicEntity;
    }

    public Map<String, String> getBasicBasic() {
        return basicBasic;
    }

    public void setBasicBasic(Map<String, String> basicBasic) {
        this.basicBasic = basicBasic;
    }

    public Map<String, Address> getBasicEmbeddable() {
        return basicEmbeddable;
    }

    public void setBasicEmbeddable(Map<String, Address> basicEmbeddable) {
        this.basicEmbeddable = basicEmbeddable;
    }

    public Map<String, Phone> getBasicEntity() {
        return basicEntity;
    }

    public void setBasicEntity(Map<String, Phone> basicEntity) {
        this.basicEntity = basicEntity;
    }
}