package org.mousepilots.es.test.domain.entities;

import org.mousepilots.es.test.domain.BaseEntity;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;
import org.mousepilots.es.test.domain.embeddables.Address;
import org.mousepilots.es.test.domain.embeddables.PhoneType;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Entity
public class EmbeddableMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //Map with embeddable-basic
    @ElementCollection(targetClass = String.class)
    @MapKeyClass(PhoneType.class)
    private Map<PhoneType, String> embeddableBasic;
    //Map with embeddable-embeddable
    @ElementCollection(targetClass = Address.class)
    @MapKeyClass(PhoneType.class)
    private Map<PhoneType, Address> embeddableEmbeddable;
    //Map with embeddable-entity
    @ManyToMany(targetEntity = Phone.class)
    @MapKeyClass(PhoneType.class)
    private Map<PhoneType, Phone> embeddableEntity;

    public EmbeddableMap() {
    }

    public EmbeddableMap(Map<PhoneType, String> embeddableBasic,
            Map<PhoneType, Address> embeddableEmbeddable,
            Map<PhoneType, Phone> embeddableEntity) {
        this.embeddableBasic = embeddableBasic;
        this.embeddableEmbeddable = embeddableEmbeddable;
        this.embeddableEntity = embeddableEntity;
    }

    public Map<PhoneType, String> getEmbeddableBasic() {
        return embeddableBasic;
    }

    public void setEmbeddableBasic(Map<PhoneType, String> embeddableBasic) {
        this.embeddableBasic = embeddableBasic;
    }

    public Map<PhoneType, Address> getEmbeddableEmbeddable() {
        return embeddableEmbeddable;
    }

    public void setEmbeddableEmbeddable(Map<PhoneType, Address> embeddableEmbeddable) {
        this.embeddableEmbeddable = embeddableEmbeddable;
    }

    public Map<PhoneType, Phone> getEmbeddableEntity() {
        return embeddableEntity;
    }

    public void setEmbeddableEntity(Map<PhoneType, Phone> embeddableEntity) {
        this.embeddableEntity = embeddableEntity;
    }

    @Override
    public int hashCode() {
        int hash = 25;
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