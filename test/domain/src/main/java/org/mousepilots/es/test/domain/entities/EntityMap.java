package org.mousepilots.es.test.domain.entities;

import org.mousepilots.es.test.domain.BaseEntity;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;
import org.mousepilots.es.test.domain.embeddables.Address;

/**
 * Entity that tests maps with Entity keys.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
public class EntityMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ElementCollection(targetClass = String.class)
    @MapKeyClass(Phone.class)
    private Map<Phone, String> entityBasic;

    @ElementCollection(targetClass = Address.class)
    @MapKeyClass(Phone.class)
    private Map<Phone, Address> entityEmbeddable;

    @ManyToMany(targetEntity = Phone.class)
    @MapKeyClass(Phone.class)
    private Map<Phone, Phone> entityEntity;

    public EntityMap() {
    }

    public EntityMap(Map<Phone, String> entityBasic, Map<Phone,
            Address> entityEmbeddable, Map<Phone, Phone> entityEntity) {
        this.entityBasic = entityBasic;
        this.entityEmbeddable = entityEmbeddable;
        this.entityEntity = entityEntity;
    }

    public Map<Phone, String> getEntityBasic() {
        return entityBasic;
    }

    public void setEntityBasic(Map<Phone, String> entityBasic) {
        this.entityBasic = entityBasic;
    }

    public Map<Phone, Address> getEntityEmbeddable() {
        return entityEmbeddable;
    }

    public void setEntityEmbeddable(Map<Phone, Address> entityEmbeddable) {
        this.entityEmbeddable = entityEmbeddable;
    }

    public Map<Phone, Phone> getEntityEntity() {
        return entityEntity;
    }

    public void setEntityEntity(Map<Phone, Phone> entityEntity) {
        this.entityEntity = entityEntity;
    }

    @Override
    public int hashCode() {
        int hash = 17;
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