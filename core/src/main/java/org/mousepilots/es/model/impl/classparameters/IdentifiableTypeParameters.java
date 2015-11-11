package org.mousepilots.es.model.impl.classparameters;

import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.model.IdentifiableTypeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.TypeES;

/**
 * This class takes the most common identifiable type parameters and
 * bundles them to save space in the constructors.
 * @author Nicky Ernste
 * @version 1.0, 11-11-2015
 */
public class IdentifiableTypeParameters<T> {

    private final SingularAttributeES<? super T, ?> id;
    private final SingularAttributeES<T, ?> declaredId;
    private final SingularAttributeES<? super T, ?> version;
    private final SingularAttributeES<T, ?> declaredVersion;
    private final IdentifiableTypeES<? super T> superType;
    private final Set<SingularAttribute<? super T, ?>> idClassAttributes;
    private final boolean singleIdAttribute, versionAttribute;
    private final TypeES<?> idType;

    public IdentifiableTypeParameters(SingularAttributeES<? super T, ?> id,
            SingularAttributeES<T, ?> declaredId,
            SingularAttributeES<? super T, ?> version,
            SingularAttributeES<T, ?> declaredVersion,
            IdentifiableTypeES<? super T> superType,
            Set<SingularAttribute<? super T, ?>> idClassAttributes,
            boolean singleIdAttribute, boolean versionAttribute,
            TypeES<?> idType) {
        this.id = id;
        this.declaredId = declaredId;
        this.version = version;
        this.declaredVersion = declaredVersion;
        this.superType = superType;
        this.idClassAttributes = idClassAttributes;
        this.singleIdAttribute = singleIdAttribute;
        this.versionAttribute = versionAttribute;
        this.idType = idType;
    }

    public SingularAttributeES<? super T, ?> getId() {
        return id;
    }

    public SingularAttributeES<T, ?> getDeclaredId() {
        return declaredId;
    }

    public SingularAttributeES<? super T, ?> getVersion() {
        return version;
    }

    public SingularAttributeES<T, ?> getDeclaredVersion() {
        return declaredVersion;
    }

    public IdentifiableTypeES<? super T> getSuperType() {
        return superType;
    }

    public boolean hasSingleIdAttribute() {
        return singleIdAttribute;
    }

    public boolean hasVersionAttribute() {
        return versionAttribute;
    }

    public TypeES<?> getIdType() {
        return idType;
    }

    public Set<SingularAttribute<? super T, ?>> getIdClassAttributes() {
        return idClassAttributes;
    }
}