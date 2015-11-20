package org.mousepilots.es.maven.model.generator.model.attribute;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.metamodel.Attribute;
import org.mousepilots.es.maven.model.generator.model.Descriptor;
import org.mousepilots.es.maven.model.generator.model.AssociationDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EmbeddableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.IdentifiableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.ManagedTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;
import org.mousepilots.es.model.AssociationTypeES;
import org.mousepilots.es.util.StringUtils;

/**
 * Descriptor of the {@link javax.persistence.metamodel.Attribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 18-11-2015
 */
public class AttributeDescriptor extends Descriptor<Attribute.PersistentAttributeType> {

    private TypeDescriptor declaringTypeDescriptor;
    private Set<AttributeDescriptor> INSTANCES = new TreeSet<>();

    public AttributeDescriptor(String name, Class javaType, int ordinal) {
        super(name, javaType, ordinal);
        INSTANCES.add(this);
    }

    public Set<AttributeDescriptor> getAll() {
        return INSTANCES;
    }

    /**
     * Try to find a getter method for this attribute.
     * @return A {@link Method} that represents the get method for this attribute.
     * @throws IllegalStateException If no getter method could be found.
     * Which could mean the JavaBeans naming convention was not used.
     */
    private Method getGetterMethod() {
        final List<String> expectedNames;
        final String suffix = getName().substring(0, 1).toUpperCase() + getName().substring(1);
        final Class declaringJavaType = getDeclaringTypeDescriptor().getJavaType();
        if (getJavaType() == Boolean.class || getJavaType() == boolean.class) {
            expectedNames = Arrays.asList(new String[]{"get" + suffix, "is" + suffix});
        } else {
            expectedNames = Arrays.asList(new String[]{"get" + suffix});
        }
        for (String expectedName : expectedNames) {
            try {
                return declaringJavaType.getMethod(expectedName);
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(AttributeDescriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new IllegalStateException("Cannot find the getter for " + declaringJavaType.getCanonicalName() + "." + getName());
    }

    /**
     * Try to find a setter method for this attribute.
     * @return A {@link Method} representing the setter method for this attribute or {@code null} if no setter was found.
     * When no setter is found it could mean that this attribute is read only, or that the JavaBeans naming convention was not followed.
     */
    private Method getSetterMethod() {
        final List<String> expectedNames;
        final String suffix = getName().substring(0, 1).toUpperCase() + getName().substring(1);
        final Class declaringJavaType = getDeclaringTypeDescriptor().getJavaType();
        expectedNames = Arrays.asList(new String[]{"set" + suffix});
        for (String expectedName : expectedNames) {
            try {
                return declaringJavaType.getMethod(expectedName);
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(AttributeDescriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean isReadOnly() {
        return getSetterMethod() == null;
    }

    public boolean isCollection() {
        return Map.class.isAssignableFrom(getJavaType()) || Collection.class.isAssignableFrom(getJavaType());
    }

    public boolean isAssociation() {
        for (TypeDescriptor typeDescriptor : TypeDescriptor.getAll()) {
            if (typeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the association this attribute has with another attribute.
     * @param associationType The type of association you want to get.
     * @return An {@link AssociationDescriptor} describing the association of this attribute with another.
     * or {@code null} if the persistence type of the attribute is Basic.
     * @throws IllegalStateException If the owning side of a bidirectional association could not be found.
     */
    public final AssociationDescriptor getAssociation(AssociationTypeES associationType) {
        // references sub-types. Implementations could be spread over sub-types,
        // but then those would have to refer to one another e.g. OneToMany <--> ManyToOne
        final Attribute.PersistentAttributeType persistentAttributeType = getPersistentAttributeType();
        switch (associationType) {
            case KEY: {
                if (this instanceof MapAttributeDescriptor) {
                    final MapAttributeDescriptor mad = MapAttributeDescriptor.class.cast(this);
                    final TypeDescriptor keyTypeDescriptor = TypeDescriptor.getInstance(mad.getKeyJavaType());
                    switch (keyTypeDescriptor.getPersistenceType()) {
                        case BASIC: {
                            return null;
                        }
                        case EMBEDDABLE: {
                            //our definition of a Map-key which is embeddable: an ElementCollection
                            return new AssociationDescriptor(this, Attribute.PersistentAttributeType.ELEMENT_COLLECTION, true, (EmbeddableTypeDescriptor) keyTypeDescriptor, null, null);
                        }

                        default: {
                            //our definition of a Map-key which is identifiable: a unidirectional OneToMany association
                            return new AssociationDescriptor(this, Attribute.PersistentAttributeType.ONE_TO_MANY, true, (IdentifiableTypeDescriptor) keyTypeDescriptor, null, null);
                        }
                    }
                } else {
                    return null;

                }
            }
            case VALUE : {
                switch (persistentAttributeType) {
                    case BASIC : {
                        return null;
                    }
                    case ELEMENT_COLLECTION: {
                        PluralAttributeDescriptor pad = (PluralAttributeDescriptor) this;
                        final TypeDescriptor elementType = pad.getElementType();
                        if (elementType instanceof EmbeddableTypeDescriptor) {
                            return new AssociationDescriptor(this, persistentAttributeType, true, (EmbeddableTypeDescriptor) elementType, null, null);
                        } else {
                            //Collection of basic values.
                            return null;
                        }
                    }
                    case EMBEDDED: {
                        //Expecting a single value attribute.
                        return new AssociationDescriptor(this, persistentAttributeType, true, TypeDescriptor.getInstance(getJavaType(), EmbeddableTypeDescriptor.class), null, null);
                    }
                    case MANY_TO_ONE: {
                        //owning side of ManyToOne
                        final IdentifiableTypeDescriptor targetTypeDescriptor = TypeDescriptor.getInstance(getJavaType(), IdentifiableTypeDescriptor.class);
                        for (PluralAttributeDescriptor inverseAttribute : targetTypeDescriptor.getAttributeDescriptors(PluralAttributeDescriptor.class)) {
                            final OneToMany oneToMany = inverseAttribute.getAnnotation(OneToMany.class);
                            if (oneToMany != null
                                    && getName().equals(oneToMany.mappedBy())
                                    && inverseAttribute.getElementType().getJavaType().isAssignableFrom(getJavaType())) {
                                //bidirectional ManyToOne relation
                                return new AssociationDescriptor(this, persistentAttributeType, true, targetTypeDescriptor, inverseAttribute, AssociationTypeES.VALUE);
                            }
                        }
                        //unidirectional ManyToOne relation
                        return new AssociationDescriptor(this, persistentAttributeType, true, targetTypeDescriptor, null, null);
                    }
                    case ONE_TO_MANY: {
                        final OneToMany oneToMany = getAnnotation(OneToMany.class);
                        final PluralAttributeDescriptor pad = (PluralAttributeDescriptor) this;
                        if (StringUtils.isNullOrEmpty(oneToMany.mappedBy())) {
                            //Owning side of unidirectional one to many
                            return new AssociationDescriptor(this, persistentAttributeType, true, (ManagedTypeDescriptor) pad.getElementType(), null, null);
                        } else {
                            final ManagedTypeDescriptor targetType = (ManagedTypeDescriptor)pad.getElementType();
                            //Non-owning side of bidirectional one to many.
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetType.getAttributeDescriptors(SingularAttributeDescriptor.class)) {
                                final ManyToOne manyToOne = inverseAttributeDescriptor.getAnnotation(ManyToOne.class);
                                if (manyToOne != null
                                        && getName().equals(oneToMany.mappedBy())
                                        && inverseAttributeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                                    final ManagedTypeDescriptor elementType = (ManagedTypeDescriptor)pad.getElementType();
                                    return new AssociationDescriptor(this, persistentAttributeType, false, elementType, inverseAttributeDescriptor, AssociationTypeES.VALUE);
                                }
                            }
                            throw new IllegalStateException("owning side of "+ persistentAttributeType + " relation not found: " + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + "." + getName());
                        }
                    }
                    case ONE_TO_ONE: {
                        final OneToOne oneToOne = getAnnotation(OneToOne.class);
                        final IdentifiableTypeDescriptor targetTypeDescriptor = TypeDescriptor.getInstance(this.getJavaType(), IdentifiableTypeDescriptor.class);
                        if (StringUtils.isNullOrEmpty(oneToOne.mappedBy())) {
                            //Owning side one to one.
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetTypeDescriptor.getAttributeDescriptors(SingularAttributeDescriptor.class)){
                                final OneToOne inverseOneToOne = inverseAttributeDescriptor.getAnnotation(OneToOne.class);
                                if (inverseOneToOne != null
                                    && getName().equals(inverseOneToOne.mappedBy())
                                    && inverseAttributeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                                    //Bidirectional One to one.
                                    return new AssociationDescriptor(this, persistentAttributeType, true, TypeDescriptor.getInstance(inverseAttributeDescriptor.getJavaType(), IdentifiableTypeDescriptor.class), inverseAttributeDescriptor, AssociationTypeES.VALUE);
                                }
                            }
                            //Unidirectional one to one.
                            return new AssociationDescriptor(this, persistentAttributeType, true, TypeDescriptor.getInstance(getJavaType(), IdentifiableTypeDescriptor.class), null, null);
                        } else {
                            //Non-owning side of one to one.
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetTypeDescriptor.getAttributeDescriptors(SingularAttributeDescriptor.class)){
                                final OneToOne inverseOneToOne = inverseAttributeDescriptor.getAnnotation(OneToOne.class);
                                if (inverseOneToOne != null
                                    && getName().equals(oneToOne.mappedBy())
                                    && inverseAttributeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                                    //Bidirectional One to one.
                                    return new AssociationDescriptor(this, persistentAttributeType, true, TypeDescriptor.getInstance(inverseAttributeDescriptor.getJavaType(), IdentifiableTypeDescriptor.class), inverseAttributeDescriptor, AssociationTypeES.VALUE);
                                }
                            }
                            throw new IllegalStateException("owning side of "+ persistentAttributeType + " relation not found: " + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + "." + getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    public String getGetterMethodName() {
        final Method getterMethod = getGetterMethod();
        return getterMethod.getName();
    }

    public String getSetterMethodName() {
        final Method setterMethod = getSetterMethod();
        return setterMethod == null ? null : setterMethod.getName();
    }

    public TypeDescriptor getDeclaringTypeDescriptor() {
        return declaringTypeDescriptor;
    }

    public void setDeclaringTypeDescriptor(TypeDescriptor declaringTypeDescriptor) {
        this.declaringTypeDescriptor = declaringTypeDescriptor;
    }

    /**
     * This method will return a {@link String} representation of the declaration and initialisation of a {@link MemberES} object for this attribute.
     * This is put in the velocity template when generating the meta model.
     * @return A {@link String} with the declaration and initialisation of a {@link MemberES} object for this attribute.
     */
    public String getMemberDeclaration() {
        final String declarerJavaTypeCanonicalName = getDeclaringTypeDescriptor().getJavaTypeCanonicalName();
        StringBuilder sb = new StringBuilder();
        sb.append("private final MemberES javaMember = new PropertyMember(");
        sb.append(declarerJavaTypeCanonicalName).append(".class, ");
        sb.append("\"").append(getName()).append("\", ");
        sb.append("(Getter<").append(declarerJavaTypeCanonicalName).append(",").append(getJavaTypeCanonicalName()).append(">)").append(declarerJavaTypeCanonicalName).append("::").append(getGetterMethodName()).append(", ");
        if (getSetterMethodName() != null) {
            sb.append("(Setter<").append(declarerJavaTypeCanonicalName).append(",").append(getJavaTypeCanonicalName()).append(">)").append(declarerJavaTypeCanonicalName).append("::").append(getSetterMethodName()).append(", ");
        } else {
            sb.append("null,");
        }
        sb.append(getGetterMethod().getModifiers()).append(");");
        return sb.toString();
    }

    /**
     * Try to get the {@link Field} for this attribute.
     * @return The {@link Field} for this attribute, or {@code null} if the field could not be found.
     */
    public Field getField() {
        for (Class clazz = getJavaType(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return getJavaType().getDeclaredField(getName());
            } catch (NoSuchFieldException | SecurityException ex) {
                //Hide the exception.
            }
        }
        return null;
    }

    /**
     * @param <T> A type that extends {@link Annotation}.
     * @param annotationClass The annotation to look for.
     * @return the first attribute found which is annotated with {@code annotationClass}
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        AnnotatedElement[] elements = new AnnotatedElement[]{getField(), getGetterMethod()};
        for (AnnotatedElement element : elements) {
            T annotation = element.getAnnotation(annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * @return the persistent attribute type based on the annotation for this
     * attribute.
     */
    public final Attribute.PersistentAttributeType getPersistentAttributeType() {
        Map<Class<? extends Annotation>, Attribute.PersistentAttributeType> typeMap = new HashMap<>();
        typeMap.put(OneToOne.class, Attribute.PersistentAttributeType.ONE_TO_ONE);
        typeMap.put(OneToMany.class, Attribute.PersistentAttributeType.ONE_TO_MANY);
        typeMap.put(ManyToOne.class, Attribute.PersistentAttributeType.MANY_TO_ONE);
        typeMap.put(ManyToMany.class, Attribute.PersistentAttributeType.MANY_TO_MANY);
        typeMap.put(ElementCollection.class, Attribute.PersistentAttributeType.ELEMENT_COLLECTION);
        typeMap.put(EmbeddedId.class, Attribute.PersistentAttributeType.EMBEDDED);

        for (Map.Entry<Class<? extends Annotation>, Attribute.PersistentAttributeType> entry : typeMap.entrySet()) {
            if (getAnnotation(entry.getKey()) != null) {
                return entry.getValue();
            }
        }

        if (TypeDescriptor.getInstance(getJavaType()) != null) {
            return Attribute.PersistentAttributeType.EMBEDDED;
        } else {
            return Attribute.PersistentAttributeType.BASIC;
        }
    }
}