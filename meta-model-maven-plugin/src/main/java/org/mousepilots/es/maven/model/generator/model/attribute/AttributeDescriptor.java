package org.mousepilots.es.maven.model.generator.model.attribute;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.impl.AssociationESImpl;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.PropertyMember;
import org.mousepilots.es.core.util.LazyValue;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.core.util.StringUtils;

/**
 * Descriptor of the {@link javax.persistence.metamodel.Attribute} of JPA.
 *
 * @author Nicky Ernste
 * @version 1.0, 4-12-2015
 */
public abstract class AttributeDescriptor extends Descriptor<Attribute.PersistentAttributeType> {

    private static final Map<Class<? extends Annotation>, Attribute.PersistentAttributeType> ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE = new HashMap<>();

    static {
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(OneToOne.class, Attribute.PersistentAttributeType.ONE_TO_ONE);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(OneToMany.class, Attribute.PersistentAttributeType.ONE_TO_MANY);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(ManyToOne.class, Attribute.PersistentAttributeType.MANY_TO_ONE);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(ManyToMany.class, Attribute.PersistentAttributeType.MANY_TO_MANY);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(ElementCollection.class, Attribute.PersistentAttributeType.ELEMENT_COLLECTION);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(EmbeddedId.class, Attribute.PersistentAttributeType.EMBEDDED);
        ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.put(Embedded.class, Attribute.PersistentAttributeType.EMBEDDED);
    }
    private static final Set<AttributeDescriptor> INSTANCES = new TreeSet<>();

    private static final Map<Class, Class> PRIMITIVE_TO_BOXED = Maps.create(
            Arrays.asList(boolean.class, byte.class, short.class, char.class, int.class, long.class, float.class, double.class),
            Arrays.asList(Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class));

    private static final Map<Class, Class> BOXED_TO_PRIMITIVE = Maps.invert(PRIMITIVE_TO_BOXED);

    private TypeDescriptor declaringTypeDescriptor;

    /**
     * Create a new instance of this descriptor.
     *
     * @param name the name of this attribute.
     * @param javaType the java type of this attribute.
     * @param ordinal the ordinal for this attribute.
     */
    public AttributeDescriptor(String name, Class javaType, int ordinal) {
        super(name, javaType, ordinal);
        INSTANCES.add(this);
    }

    /**
     * Get all the registered {@link AttributeDescriptor}s.
     *
     * @return a set of all registered attribute descriptors.
     */
    public static Set<AttributeDescriptor> getAll() {
        return INSTANCES;
    }

    private String getterSetterSuffix() {
        final String name = getName();
        final String suffix = name.substring(0, 1).toUpperCase() + name.substring(1);
        return suffix;
    }

    @Override
    protected String getDeclaredVariableName() {
        return getName();
    }

    /**
     * Try to find a getter method for this attribute.
     *
     * @return A {@link Method} that represents the get method for this
     * attribute.
     * @throws IllegalStateException If no getter method could be found. Which
     * could mean the JavaBeans naming convention was not used.
     */
    public Method getGetterMethod() {
        final List<String> expectedNames;
        final Class declaringJavaType = getDeclaringTypeDescriptor().getJavaType();
        final String suffix = getterSetterSuffix();
        if (getJavaType() == Boolean.class || getJavaType() == boolean.class) {
            expectedNames = Arrays.asList(new String[]{"get" + suffix, "is" + suffix});
        } else {
            expectedNames = Arrays.asList(new String[]{"get" + suffix});
        }
        for (String expectedName : expectedNames) {
            try {
                return declaringJavaType.getMethod(expectedName);
            } catch (NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(AttributeDescriptor.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        throw new IllegalStateException("Cannot find the getter for property " + declaringJavaType.getCanonicalName() + "." + getName());
    }

    /**
     * Try to find a setter method for this attribute.
     *
     * @return A {@link Method} representing the setter method for this
     * attribute or {@code null} if no setter was found. When no setter is found
     * it could mean that this attribute is read only, or that the JavaBeans
     * naming convention was not followed.
     */
    public Method getSetterMethod() {
        final Class declaringJavaType = getDeclaringTypeDescriptor().getJavaType();
        final List<String> expectedNames = Arrays.asList(new String[]{"set" + getterSetterSuffix()});
        //try javaType first to prevent unnecessary warnings
        final Set<Class> eligbleTypes = new LinkedHashSet<>();
        final Class javaType = getJavaTypeDescriptor().getJavaType();
        eligbleTypes.add(javaType);
        if (PRIMITIVE_TO_BOXED.containsKey(javaType)) {
            eligbleTypes.add(PRIMITIVE_TO_BOXED.get(javaType));
        }
        if (BOXED_TO_PRIMITIVE.containsKey(javaType)) {
            eligbleTypes.add(BOXED_TO_PRIMITIVE.get(javaType));
        }
        for (Class eligibleType : eligbleTypes) {
            for (String expectedName : expectedNames) {
                try {
                    return declaringJavaType.getMethod(expectedName, eligibleType);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(AttributeDescriptor.class.getName()).log(Level.WARNING, "cannot find method {0} with parameter {1} on {2}", new Object[]{expectedName, eligibleType, declaringJavaType});
                } catch (SecurityException ex) {
                    Logger.getLogger(AttributeDescriptor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public Set<AttributeDescriptor> getSubDescriptors() {
        Set<AttributeDescriptor> subAttributes = new HashSet<>();
        for (TypeDescriptor td : getDeclaringTypeDescriptor().getSubTypes()) {
            final AttributeDescriptor subAttribute = td.getAttribute(getName());
            if (subAttribute != null && this != subAttribute) {
                subAttributes.add(subAttribute);
            }
        }
        return subAttributes;
    }

    /**
     * Check if this attribute is read only. Read-only meaning there is no way
     * to set the value of this attribute.
     *
     * @return {@code true} if this attribute is read only, {@code false}
     * otherwise.
     */
    public boolean isReadOnly() {
        return getSetterMethod() == null;
    }

    /**
     * Check if this attribute is a collection.
     *
     * @return {@code true} if this attribute is a collection, {@code false}
     * otherwise.
     */
    public boolean isCollection() {
        return Map.class.isAssignableFrom(getJavaType())
                || Collection.class.isAssignableFrom(getJavaType());
    }

    /**
     * Check if the attribute is part of an association with another attribute.
     *
     * @return {@code true} if this attribute is part of an association,
     * {@code false} otherwise.
     */
    public boolean isAssociation() {
        for (TypeDescriptor typeDescriptor : TypeDescriptor.getAll()) {
            if (typeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the super attribute for this attribute. A super attribute is an
     * attribute with the same name, declared on a super class of {@code this}
     * declaring type.
     *
     * @return the super attribute for this attribute if existent. Otherwise
     * {@code null}.
     */
    public AttributeDescriptor getSuper() {
        TypeDescriptor aSuper = getDeclaringTypeDescriptor().getSuper();
        if (aSuper == null) {
            return null;
        } else {
            return aSuper.getAttribute(getName());
        }
    }

    private final LazyValue<AssociationDescriptor> valueAssociationDesciptor = new LazyValue<>(() -> {
        return this.createAssociation(AssociationTypeES.VALUE);
    });
    private final LazyValue<AssociationDescriptor> keyAssociationDesciptor = new LazyValue<>(() -> {
        return this.createAssociation(AssociationTypeES.KEY);
    });

    public final AssociationDescriptor getAssociation(AssociationTypeES associationType) {
        switch (associationType) {
            case KEY:
                return keyAssociationDesciptor.getValue();
            case VALUE:
                return valueAssociationDesciptor.getValue();
            default:
                throw new IllegalArgumentException(associationType.name());
        }
    }

    /**
     * Get the association this attribute has with another attribute.
     *
     * @param associationType The type of association you want to get.
     * @return An {@link AssociationDescriptor} describing the association of
     * this attribute with another. or {@code null} if the persistence type of
     * the attribute is Basic. Or {@code null} if the {@code associationType} is
     * {@code KEY} but {@code this} is not an instance of
     * {@link MapAttributeDescriptor}.
     * @throws IllegalStateException If the owning side of a bidirectional
     * association could not be found.
     */
    private AssociationDescriptor createAssociation(AssociationTypeES associationType) {
        /* references sub-types. Implementations could be spread over sub-types,
         but then those would have to refer to one another
         e.g. OneToMany <--> ManyToOne*/
        final Attribute.PersistentAttributeType persistentAttributeType = getPersistentAttributeType();
        if(getJavaType()==String.class){
            System.out.println("String");
        }
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
            case VALUE: {
                switch (persistentAttributeType) {
                    case BASIC: {
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
                        final EmbeddableTypeDescriptor embeddableTypeDescriptor = TypeDescriptor.getInstance(getJavaType(), EmbeddableTypeDescriptor.class);
                        return new AssociationDescriptor(this, persistentAttributeType, true, embeddableTypeDescriptor, null, null);
                    }
                    case MANY_TO_MANY: {
                        final ManyToMany manyToMany = getAnnotation(ManyToMany.class);
                        final PluralAttributeDescriptor pad = (PluralAttributeDescriptor) this;
                        final ManagedTypeDescriptor targetType = (ManagedTypeDescriptor) pad.getElementType();

                        final boolean owner = StringUtils.isNullOrEmpty(manyToMany.mappedBy());
                        for (PluralAttributeDescriptor inverseAttributeDescriptor : targetType.getAttributeDescriptors(PluralAttributeDescriptor.class)) {
                            final boolean elementTypesMatch
                                    = inverseAttributeDescriptor.getElementType().getJavaType()
                                    .isAssignableFrom(getDeclaringTypeDescriptor().getJavaType());

                            if (elementTypesMatch) {
                                final ManyToMany inverseManyToMany = inverseAttributeDescriptor.getAnnotation(ManyToMany.class);
                                if (inverseManyToMany != null) {
                                    final boolean inverseFound
                                            = owner && getName().equals(inverseManyToMany.mappedBy())
                                            || !owner && inverseAttributeDescriptor.getName().equals(manyToMany.mappedBy());
                                    if (inverseFound) {
                                        return new AssociationDescriptor(
                                                this,
                                                persistentAttributeType,
                                                owner,
                                                (ManagedTypeDescriptor) pad.getElementType(),
                                                inverseAttributeDescriptor,
                                                AssociationTypeES.VALUE
                                        );

                                    }
                                }

                            }
                        }

                        //unidirectional ManyToMany
                        return new AssociationDescriptor(
                                this,
                                persistentAttributeType,
                                owner,
                                (ManagedTypeDescriptor) pad.getElementType(),
                                null,
                                null
                        );

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
                            final ManagedTypeDescriptor targetType = (ManagedTypeDescriptor) pad.getElementType();
                            //Non-owning side of bidirectional one to many.
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetType.getAttributeDescriptors(SingularAttributeDescriptor.class)) {
                                final ManyToOne manyToOne = inverseAttributeDescriptor.getAnnotation(ManyToOne.class);
                                if (manyToOne != null
                                        && getName().equals(oneToMany.mappedBy())
                                        && inverseAttributeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                                    final ManagedTypeDescriptor elementType = (ManagedTypeDescriptor) pad.getElementType();
                                    return new AssociationDescriptor(this, persistentAttributeType, false, elementType, inverseAttributeDescriptor, AssociationTypeES.VALUE);
                                }
                            }
                            throw new IllegalStateException("owning side of " + persistentAttributeType + " relation not found: " + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + "." + getName());
                        }
                    }
                    case ONE_TO_ONE: {
                        final OneToOne oneToOne = getAnnotation(OneToOne.class);
                        final IdentifiableTypeDescriptor targetTypeDescriptor = TypeDescriptor.getInstance(this.getJavaType(), IdentifiableTypeDescriptor.class);
                        if (StringUtils.isNullOrEmpty(oneToOne.mappedBy())) {
                            //Owning side one to one.
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetTypeDescriptor.getAttributeDescriptors(SingularAttributeDescriptor.class)) {
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
                            for (SingularAttributeDescriptor inverseAttributeDescriptor : targetTypeDescriptor.getAttributeDescriptors(SingularAttributeDescriptor.class)) {
                                final OneToOne inverseOneToOne = inverseAttributeDescriptor.getAnnotation(OneToOne.class);
                                if (inverseOneToOne != null
                                        && getName().equals(oneToOne.mappedBy())
                                        && inverseAttributeDescriptor.getJavaType().isAssignableFrom(getJavaType())) {
                                    //Bidirectional One to one.
                                    return new AssociationDescriptor(this, persistentAttributeType, true, TypeDescriptor.getInstance(inverseAttributeDescriptor.getJavaType(), IdentifiableTypeDescriptor.class), inverseAttributeDescriptor, AssociationTypeES.VALUE);
                                }
                            }
                            throw new IllegalStateException("owning side of " + persistentAttributeType + " relation not found: " + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + "." + getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the name of the getter method for this attribute.
     *
     * @return the name of the getter method or {@code null} if the getter
     * method was not found.
     */
    public String getGetterMethodName() {
        final Method getterMethod = getGetterMethod();
        return getterMethod == null ? null : getterMethod.getName();
    }

    /**
     * Get the name of the setter method for this attribute if it exists.
     *
     * @return the name of the setter method or {@code null} if the attribute is
     * read only.
     */
    public String getSetterMethodName() {
        final Method setterMethod = getSetterMethod();
        return setterMethod == null ? null : setterMethod.getName();
    }

    /**
     * Get the descriptor that is the declarer of this attribute.
     *
     * @return the declarer of this attribute.
     */
    public TypeDescriptor getDeclaringTypeDescriptor() {
        return declaringTypeDescriptor;
    }

    /**
     * @return {@code this} attribute's javaType's descriptor
     */
    public TypeDescriptor getJavaTypeDescriptor() {
        return TypeDescriptor.getInstance(getJavaType());
    }

    /**
     * Set the descriptor that is the declarer of this attribute.
     *
     * @param declaringTypeDescriptor the declarer of this attribute.
     */
    public void setDeclaringTypeDescriptor(TypeDescriptor declaringTypeDescriptor) {
        this.declaringTypeDescriptor = declaringTypeDescriptor;
    }

    public String getProxyGetterDeclaration(TypeDescriptor hasAttribute) {
        return null;
    }

    public String getProxySetterDeclaration(TypeDescriptor hasAttribute) {
        return null;
    }

    /**
     * Try to get the {@link Field} for this attribute.
     *
     * @return The {@link Field} for this attribute, or {@code null} if the
     * field could not be found.
     */
    public Field getField() {
        for (Class clazz = getDeclaringTypeDescriptor().getJavaType(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                //Cannot get the field name on the type of the attribute.
                return getDeclaringTypeDescriptor().getJavaType().getDeclaredField(getName());
            } catch (NoSuchFieldException | SecurityException ex) {
                //Hide the exception.
            }
        }
        return null;
    }

    /**
     * @param <T> A type that extends {@link Annotation}.
     * @param annotationClass The annotation to look for.
     * @return the first attribute found which is annotated with
     * {@code annotationClass}
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
        for (Map.Entry<Class<? extends Annotation>, Attribute.PersistentAttributeType> entry : ANNOTATION_TO_PERSISTENT_ATTRIBUTE_TYPE.entrySet()) {
            if (getAnnotation(entry.getKey()) != null) {
                return entry.getValue();
            }
        }
        return Attribute.PersistentAttributeType.BASIC;
    }

    public abstract Class< ? extends AttributeES> getDeclaredClass();

    public abstract Class< ? extends AttributeESImpl> getImplementationClass();

    /**
     * @return the {@code this}' generic java-type, obtained from {@code this}
     * field or getter
     */
    public final Type getGenericJavaType() {
        final Field field = getField();
        if (field != null) {
            return field.getGenericType();
        } else {
            return getGetterMethod().getGenericReturnType();
        }
    }

    public abstract String getGenericsString();

    protected final String getAssociationInstantiation(AssociationTypeES associationType) {
        final AssociationDescriptor a = getAssociation(associationType);
        if (a == null) {
            return "null";
        } else {
            final AssociationDescriptor inverse = a.getInverse();
            final String retval
                    = "new " + AssociationESImpl.class.getCanonicalName() + "<>("
                    + String.join(
                            ", ",
                            a.getOrdinal().toString(),
                            a.getSourceAttribute().getOrdinal().toString(),
                            a.getTargetTypeDescriptor().getOrdinal().toString(),
                            inverse == null ? "null" : inverse.getOrdinal().toString(),
                            a.isOwner().toString(),
                            AssociationTypeES.class.getCanonicalName() + "." + associationType.name(),
                            Attribute.PersistentAttributeType.class.getCanonicalName() + "." + a.getPersistentAttributeType().name()
                    )
                    + ")";
            return retval;
        }
    }

    private String getPropertyMemberInstantiation() {
        final String declaringClassCanonicalName = getDeclaringTypeDescriptor().getJavaTypeCanonicalName();
        final StringBuilder retval = new StringBuilder()
                .append("new ").append(PropertyMember.class.getCanonicalName())
                .append("<>(")
                .append(declaringClassCanonicalName).append(".class, ")
                .append("\"").append(getName()).append("\", ")
                .append(getGetterMethodName() == null ? "null" : declaringClassCanonicalName + "::" + getGetterMethodName()).append(", ")
                .append(getSetterMethodName() == null ? "null" : declaringClassCanonicalName + "::" + getSetterMethodName()).append(", ")
                .append(getGetterMethod().getModifiers())
                .append(")");
        return retval.toString();
    }

    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final AttributeDescriptor superAttribute = getSuper();
        final Set<AttributeDescriptor> subDescriptors = getSubDescriptors();
        final Map<String, String> ca = new HashMap<>();
        ca.put("name", StringUtils.quote(getName()));
        ca.put("ordinal", getOrdinal().toString());
        ca.put("typeOrdinal", getJavaTypeDescriptor().getOrdinal().toString());
        ca.put("declaringTypeOrdinal", getDeclaringTypeDescriptor().getOrdinal().toString());
        ca.put("superOrdinal", superAttribute == null ? "null" : superAttribute.getOrdinal().toString());
        ca.put(
                "subOrdinals",
                subDescriptors.isEmpty()
                        ? "java.util.Collections.emptySet()"
                        : "java.util.Arrays.asList(" + StringUtils.join(subDescriptors, a -> a.getOrdinal().toString(), ", ") + ")"
        );
        ca.put("persistentAttributeType", "javax.persistence.metamodel.Attribute.PersistentAttributeType." + getPersistentAttributeType().name());
        ca.put("javaMember", getPropertyMemberInstantiation());
        ca.put("valueAssociation", getAssociationInstantiation(AssociationTypeES.VALUE));
        return ca;
    }

    public abstract <I, O> O accept(AttributeDescriptorVisitor<I, O> visitor, I arg);

}
