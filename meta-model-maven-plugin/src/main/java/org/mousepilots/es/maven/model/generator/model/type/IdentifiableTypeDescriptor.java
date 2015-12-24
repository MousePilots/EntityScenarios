package org.mousepilots.es.maven.model.generator.model.type;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SingularAttributeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.IdentifiableType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class IdentifiableTypeDescriptor extends ManagedTypeDescriptor {

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     */
    public IdentifiableTypeDescriptor(Class<?> metaModelClass,
            PersistenceType persistenceType, String name, Class javaType,
            int ordinal){
        super(metaModelClass, persistenceType, name, javaType, ordinal);
    }

    /**
     * Get the attribute that forms the id of this identifiable type.
     * @return the attribute that forms the id.
     */
    public AttributeDescriptor getId() {
        AttributeDescriptor ad = getAttributeAnnotatedWith(Id.class);
        if (ad == null) {
            if (getSuperType() != null) {
                ad = getSuperType().getId(); //If this type does not contain an id check its super type.
            }
            //Should not happen with identifiables, since they or their super type always needs an id.
        }
        return ad;
    }

    /**
     * Check whether or not the id for this attribute is generated.
     * @return {@code true} if the id for this attribute is generated, {@code false} otherwise.
     */
    public boolean hasGeneratedId(){
        return getId().getAnnotation(GeneratedValue.class)!=null;
    }

    /**
     * Get the attribute that is the version of this identifiable type.
     * @return the attribute that is the version.
     */
    public AttributeDescriptor getVersion(){
        AttributeDescriptor ad = getAttributeAnnotatedWith(Version.class);
        if (ad == null) {
            if (getSuperType() != null) {
                ad = getSuperType().getVersion(); //If this type does not contain a version check its super type.
            }
            //Should not happen with identifiables, since they or their super type always need a version.
        }
        return ad;
    }

    /**
     * Check whether the identifiable type has a version attribute.
     * @return {@code true} if this identifiable has a version attribute.
     */
    public boolean hasVersionAttribute(){
        return getVersion()!=null;
    }

    /**
     * Check whether the identifiable type has a single id attribute.
     * @return {@code true} if the id is a simple or embedded id, {@code false} if the id is a class.
     */
    public boolean hasSingleIdAttribute(){
        return true;
    }

    /**
     * Get the attributes corresponding to the id class of the identifiable type.
     * @return a set of the attributes that make up the id.
     */
    public Set<SingularAttributeDescriptor> getIdClassAttribute(){
        return Collections.singleton(getId());
    }

    /**
     * Get the type that represents the type of the id.
     * @return the type of the id.
     */
    public TypeDescriptor getIdType(){
        return TypeDescriptor.getInstance(getId().getJavaType());
    }

    /**
     * Get the attribute that corresponds to the id attribute declared by the entity or mapped superclass.
     * @return the declared id attribute.
     */
    public SingularAttributeDescriptor getDeclaredId(){
        final AttributeDescriptor id = getId();
        return id.getDeclaringTypeDescriptor() == this ? (SingularAttributeDescriptor) id : null;
    }

    /**
     * Get the attribute that corresponds to the version attribute declared by the entity or mapped superclass.
     * @return the declared version attribute.
     */
    public SingularAttributeDescriptor getDeclaredVersion(){
        final AttributeDescriptor version = getVersion();
        return version.getDeclaringTypeDescriptor() == this ? (SingularAttributeDescriptor) version : null;
    }

    /**
     * Get the identifiable type that corresponds to the most specific mapped superclass or entity extended by the entity or mapped superclass.
     * @return the super type.
     */
    public IdentifiableTypeDescriptor getSuperType(){
        final TypeDescriptor superType = TypeDescriptor.getInstance(getJavaType().getSuperclass());
        if(superType instanceof IdentifiableTypeDescriptor){
            return (IdentifiableTypeDescriptor) superType;
        } else {
            return null;
        }
    }
}