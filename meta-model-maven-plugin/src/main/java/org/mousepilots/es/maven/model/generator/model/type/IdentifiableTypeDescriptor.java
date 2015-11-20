package org.mousepilots.es.maven.model.generator.model.type;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SingularAttributeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.IdentifiableType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class IdentifiableTypeDescriptor extends ManagedTypeDescriptor {

    public IdentifiableTypeDescriptor(Type.PersistenceType persistenceType, String name, Class javaType, int ordinal) {
        super(persistenceType, name, javaType, ordinal);
    }

    public AttributeDescriptor getId(){
        return getAttributeAnnotatedWith(Id.class);
    }

    public boolean hasGeneratedId(){
        return getId().getAnnotation(GeneratedValue.class)!=null;
    }

    public AttributeDescriptor getVersion(){
        return getAttributeAnnotatedWith(Version.class);
    }

    public boolean hasVersionAttribute(){
        return getVersion()!=null;
    }

    public boolean hasSingleIdAttribute(){
        return true;
    }

    public Set<SingularAttributeDescriptor> getIdClassAttribute(){
        return Collections.singleton(getId());
    }

    public TypeDescriptor getIdType(){
        return TypeDescriptor.getInstance(getId().getJavaType());
    }

    public SingularAttributeDescriptor getDeclaredId(){
        final AttributeDescriptor id = getId();
        return id.getDeclaringTypeDescriptor() == this ? (SingularAttributeDescriptor) id : null;
    }

    public SingularAttributeDescriptor getDeclaredVersion(){
        final AttributeDescriptor version = getVersion();
        return version.getDeclaringTypeDescriptor() == this ? (SingularAttributeDescriptor) version : null;
    }

    public IdentifiableTypeDescriptor getSuperType(){
        final TypeDescriptor superType = TypeDescriptor.getInstance(getJavaType().getSuperclass());
        if(superType instanceof IdentifiableTypeDescriptor){
            return (IdentifiableTypeDescriptor) superType;
        } else {
            return null;
        }
    }
}