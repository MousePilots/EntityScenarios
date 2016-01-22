package org.mousepilots.es.maven.model.generator.plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.impl.BasicTypeESImpl;
import org.mousepilots.es.core.model.impl.CollectionAttributeESImpl;
import org.mousepilots.es.core.model.impl.DescendingLongGenerator;
import org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl;
import org.mousepilots.es.core.model.impl.EntityTypeESImpl;
import org.mousepilots.es.core.model.impl.Getter;
import org.mousepilots.es.core.model.impl.MappedSuperclassTypeESImpl;
import org.mousepilots.es.core.model.impl.PropertyMember;
import org.mousepilots.es.core.model.impl.Setter;
import org.mousepilots.es.core.model.impl.SingularAttributeESImpl;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.CollectionAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.ListAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.MapAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SetAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SingularAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.BasicTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EmbeddableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.EntityTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.IdentifiableTypeDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.MappedSuperClassDescriptor;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Utilities class that provides string representations of meta model
 * implementation classes that can be used when generating the meta model.
 * @author Nicky Ernste
 * @version 1.0, 10-12-2015
 */
public final class DescriptorUtils {

    //TODO merge some parts of certain methods that are redundant.

    /**
     * Get a string representation for initialising the specified {@link SingularAttributeDescriptor}.
     * @param sad the singular attribute descriptor to get the initialisation string for.
     * @return a string representation to initialise the specified {@code sad}, or the string null if {@code sad} was {@code null}.
     */
    public static String printSingularAttributeImpl(SingularAttributeDescriptor sad){
        if (sad == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(SingularAttributeESImpl.class.getCanonicalName()).append("<>(");
        boolean isDeclarerIdentifiable = IdentifiableTypeDescriptor.getInstance(sad.getDeclaringTypeDescriptor().getJavaType()) != null;
        if (isDeclarerIdentifiable && sad.getAnnotation(Generated.class) != null) {
            sb.append("true, ");
            sb.append("new ").append(DescendingLongGenerator.class.getCanonicalName()).append("(),");
        } else {
            sb.append("false, ");
            sb.append("null, ");
        }
        if (isDeclarerIdentifiable && sad.getAnnotation(Id.class) != null) {
            sb.append("true, ");
        } else {
            sb.append("false, ");
        }
        if (isDeclarerIdentifiable && sad.getAnnotation(Version.class) != null) {
            sb.append("true, ");
        } else {
            sb.append("false, ");
        }
        sb.append(sad.isOptional()).append(", ");
        sb.append("null, "); //TODO generate type.
        sb.append("Bindable.BindableType.").append(sad.getBindableType().name()).append(", ");
        sb.append(sad.getBindableJavaType().getCanonicalName()).append(".class, ");
        sb.append("\"").append(sad.getName()).append("\", ");
        sb.append(sad.getOrdinal()).append(", ");
        sb.append(sad.getJavaTypeCanonicalName()).append(".class, ");
        sb.append("Attribute.PersistentAttributeType.").append(sad.getPersistentAttributeType().name()).append(", ");
        sb.append(printMember(sad, false)).append(", ");
        sb.append(sad.isReadOnly()).append(", ");
        sb.append(sad.isAssociation()).append(", ");
        sb.append("null, "); //TODO get declaring type. Causes stackoverflow.
        sb.append("null, "); //TODO get change constructor.
        sb.append("null"); //TODO get dto constructor.
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get the string representation for initialising the specified {@Link CollectionAttributeDescriptor}.
     * @param cad the collection attribute descriptor to get the initialisation string for.
     * @return a String representation to initialise the specified {@code cad}, or the String null if {@code cad} was {@code null}.
     */
    public static String printCollectionAttributeImpl(CollectionAttributeDescriptor cad){
        if (cad == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(CollectionAttributeESImpl.class.getCanonicalName()).append("(");
        sb.append(printType(cad.getElementType())).append(", ");
        sb.append("Bindable.BindableType.").append(cad.getBindableType().name()).append(", ");
        sb.append(cad.getBindableJavaType().getCanonicalName()).append(".class, ");
        sb.append("\"").append(cad.getName()).append("\", ");
        sb.append(cad.getOrdinal()).append(", ");
        sb.append(cad.getJavaTypeCanonicalName()).append(".class, ");
        sb.append("Attribute.PersistentAttributeType.").append(cad.getPersistentAttributeType().name()).append(", ");
        sb.append(printMember(cad, false)).append(", ");
        sb.append(cad.isReadOnly()).append(", ");
        sb.append(cad.isAssociation()).append(", ");
        sb.append("null, "); //TODO get declaring type. Causes stackoverflow.
        sb.append("null, "); //TODO get change constructor.
        sb.append("null"); //TODO get dto constructor.
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get a string representation for initialising the specified {@link BasicTypeDescriptor}.
     * @param btd the basic type descriptor to get the initialisation string for.
     * @return a string representation to initialise the specified {@code btd}, or the string null if {@code btd} was {@code null}.
     */
    public static String printBasicTypeImpl(BasicTypeDescriptor btd){
        if (btd == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(BasicTypeESImpl.class.getCanonicalName()).append("(");
        sb.append("\"").append(btd.getName()).append("\", ");
        sb.append(btd.getOrdinal()).append(", ");
        sb.append(btd.getJavaType().getCanonicalName()).append(".class, ");
        sb.append("Type.PersistenceType.").append(btd.getPersistenceType().name()).append(", ");
        sb.append("\"").append(btd.getJavaTypeSimpleName()).append("\", ");
        sb.append(btd.isInstantiable()).append(", ");
        sb.append("null, ");
        if (btd.getSuper() != null) {
            sb.append(btd.getSuper().getOrdinal()).append(", ");
        } else {
            sb.append(-1).append(", ");
        }
        sb.append("null"); //Subtypes
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get a String representation for initialising the id class attributes of a type.
     * @param idClassAttributes a Set of {@link SingularAttributeDescriptor}s that represent the id class attributes for a type.
     * @return a String representation to initialise the specified {@code idClassAttributes}, or the string null if {@code idClassAttributes} was {@code null}.
     */
    public static String printIdClassAttributes(Set<SingularAttributeDescriptor> idClassAttributes){
        if (idClassAttributes == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(HashSet.class.getCanonicalName()).append("(").append(Arrays.class.getCanonicalName()).append(".asList(");
        final Iterator<SingularAttributeDescriptor> it = idClassAttributes.iterator();
        while (it.hasNext()){
            sb.append(it.next().getOrdinal());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("))");
        return sb.toString();
    }

    /**
     * Get the string representation of all attributes as a {@link HashSet}.
     * @param attributes the set of attributes to get the string representation of.
     * @return a string representation to initialise the set of attributes, or the string null if {@code attributes} was {@code null} or empty.
     */
    public static String printAttributesList(Set<? extends AttributeDescriptor> attributes){
        if (attributes == null || attributes.isEmpty()) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(HashSet.class.getCanonicalName()).append("(").append(Arrays.class.getCanonicalName()).append(".asList(");
        Iterator<? extends AttributeDescriptor> it = attributes.iterator();
        while (it.hasNext()){
            sb.append(it.next().getOrdinal());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("))");
        return sb.toString();
    }

    /**
     * Get the string representation to initialise a {@link TypeDescriptor}
     * This method will call a different method depending on the {@link Type.PersistenceType} of {@code td}.
     * @param td the type descriptor to get the initialisation string for.
     * @return a string representation to initialise a type, or the string null if {@code td} is null.
     */
    public static String printType(TypeDescriptor td){
        if (td == null) {
            return "null";
        }
        switch (td.getPersistenceType()){
            case BASIC:
                return printBasicTypeImpl((BasicTypeDescriptor) td);
            case EMBEDDABLE:
                return printEmbeddableType((EmbeddableTypeDescriptor) td);
            case MAPPED_SUPERCLASS:
                return printMappedSupperClass((MappedSuperClassDescriptor) td);
            case ENTITY:
                return printEntityType((EntityTypeDescriptor) td);
            default:
                return "null";
        }
    }

    /**
     * Get the string representation to initialise an {@link AttributeDescriptor}
     * This method will call a different method depending on the class of {@code ad}.
     * @param ad the attribute descriptor to get the initialisation string for.
     * @return a string representation to initialise an attribute, or the string null if {@code ad} is null.
     */
    public static String printAttribute(AttributeDescriptor ad){
        if (ad == null) {
            return "null";
        }
        final Class adType = ad.getClass();
        if (adType.isAssignableFrom(SingularAttributeDescriptor.class)) {
            return printSingularAttributeImpl((SingularAttributeDescriptor) ad);
        } else if (adType.isAssignableFrom(CollectionAttributeDescriptor.class)) {
            //return printCollectionAttributeImpl((CollectionAttributeDescriptor) ad);
            return "null";
        } else if (adType.isAssignableFrom(ListAttributeDescriptor.class)) {
            return "null";
        } else if (adType.isAssignableFrom(MapAttributeDescriptor.class)) {
            return "null";
        } else if (adType.isAssignableFrom(SetAttributeDescriptor.class)) {
            return "null";
        } else {
            return "null";
        }
    }

    /**
     * Get the string representation to initialise a {@link EmbeddableTypeESImpl}.
     * @param etd the {@link EmbeddableTypeDescriptor} to get the initialisation string for.
     * @return a string representation to initialise a embeddable type, or the string null if {@code etd} is {@code null}.
     */
    public static String printEmbeddableType(EmbeddableTypeDescriptor etd){
        if (etd == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(EmbeddableTypeESImpl.class.getCanonicalName()).append("(");
        sb.append("\"").append(etd.getName()).append("\", ");
        sb.append(etd.getOrdinal()).append(", ");
        sb.append(etd.getJavaTypeCanonicalName()).append(".class, ");
        sb.append("Type.PersistenceType.").append(etd.getPersistenceType().name()).append(", ");
        sb.append("\"").append(etd.getJavaTypeSimpleName()).append("\", ");
        sb.append(etd.isInstantiable()).append(", ");
        sb.append(etd.getMetaModelClass().getCanonicalName()).append(".class, ");
        sb.append(printAttributesList(etd.getAttributes())).append(", ");
        sb.append(printType(etd.getSuper())).append(", ");
        sb.append("null"); //TODO print sub types.
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get the String representation to initialise a {@link MappedSuperClassDescriptor}.
     * @param mscd the {@link MappedSuperClassDescriptor} to get the initialisation string for.
     * @return a String representation to initialise a mapped super class, or the string null if {@code mscd} was {@code null}.
     */
    public static String printMappedSupperClass(MappedSuperClassDescriptor mscd){
        if (mscd == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(MappedSuperclassTypeESImpl.class.getCanonicalName()).append("(");
        sb.append(printAttribute(mscd.getId())).append(", ");
        if (mscd.getDeclaredId() != null) {
            sb.append(printAttribute(mscd.getDeclaredId())).append(", ");
        } else {
            sb.append("null, ");
        }
        sb.append(printAttribute(mscd.getVersion())).append(", ");
        if (mscd.getDeclaredVersion() != null) {
            sb.append(printAttribute(mscd.getDeclaredVersion())).append(", ");
        } else {
            sb.append("null, ");
        }
        sb.append(printAttributesList(mscd.getIdClassAttribute())).append(", ");
        if (mscd.getIdClassAttribute().size() > 1) { //Check if this type has a simple primary key.
            sb.append("false, ");
        } else {
            sb.append("true, ");
        }
        if (mscd.getVersion() != null || mscd.getDeclaredVersion() != null) { //Check if this type has a version attribute.
            sb.append("true, ");
        } else {
            sb.append("false, ");
        }
        sb.append(printType(mscd.getIdType())).append(", ");
        sb.append("\"").append(mscd.getName()).append("\", ");
        sb.append(mscd.getOrdinal()).append(", ");
        sb.append(mscd.getJavaTypeCanonicalName()).append(".class, ");
        sb.append("Type.PersistenceType.").append(mscd.getPersistenceType().name()).append(", ");
        sb.append("\"").append(mscd.getJavaTypeSimpleName()).append("\", ");
        sb.append(mscd.isInstantiable()).append(", ");
        sb.append(mscd.getMetaModelClass().getCanonicalName()).append(".class, ");
        sb.append(printAttributesList(mscd.getAttributes())).append(", ");
        sb.append(printType(mscd.getSuper())).append(", ");
        sb.append("null"); //TODO print sub types.
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get the String representation to initialise a {@link EntityTypeDescriptor}.
     * @param etd the {@link EntityTypeDescriptor} to get the initialisation string for.
     * @return a String representation to initialise a entity type, or the string null if {@code etd} was {@code null}.
     */
    public static String printEntityType(EntityTypeDescriptor etd){
        if (etd == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder("new ").append(EntityTypeESImpl.class.getCanonicalName()).append("(");
        sb.append("Bindable.BindableType.").append(Bindable.BindableType.ENTITY_TYPE.name()).append(", ");
        sb.append(etd.getJavaTypeCanonicalName()).append(".class, ");
        sb.append(printAttribute(etd.getId())).append(", ");
        if (etd.getDeclaredId() != null) {
            sb.append(printAttribute(etd.getDeclaredId())).append(", ");
        } else {
            sb.append("null, ");
        }
        sb.append(printAttribute(etd.getVersion())).append(", ");
        if (etd.getDeclaredVersion() != null) {
            sb.append(printAttribute(etd.getDeclaredVersion())).append(", ");
        } else {
            sb.append("null, ");
        }
        sb.append(printAttributesList(etd.getIdClassAttribute())).append(", ");
        if (etd.getIdClassAttribute().size() > 1) { //Check if this type has a simple primary key.
            sb.append("false, ");
        } else {
            sb.append("true, ");
        }
        if (etd.getVersion() != null || etd.getDeclaredVersion() != null) { //Check if this type has a version attribute.
            sb.append("true, ");
        } else {
            sb.append("false, ");
        }
        sb.append(printType(etd.getIdType())).append(", ");
        sb.append("\"").append(etd.getName()).append("\", ");
        sb.append(etd.getOrdinal()).append(", ");
        sb.append(etd.getJavaTypeCanonicalName()).append(".class, ");
        sb.append("Type.PersistenceType.").append(etd.getPersistenceType().name()).append(", ");
        sb.append("\"").append(etd.getJavaTypeSimpleName()).append("\", ");
        sb.append(etd.isInstantiable()).append(", ");
        sb.append(etd.getMetaModelClass().getCanonicalName()).append(".class, ");
        sb.append(printAttributesList(etd.getAttributes())).append(", ");
        sb.append(printType(etd.getSuper())).append(", ");
        sb.append("null"); //TODO print sub types.
        sb.append(")");
        return sb.toString();
    }

    /**
     * Get the String representation to initialise the sub types of a {@link TypeDescriptor}.
     * @param td the {@link TypeDescriptor} to get the initialisation string for.
     * @return a String representation to initialise the sub types of a type descriptor, or the string null if {@code mscd} was {@code null}.
     */
    public static String printSubTypes(TypeDescriptor td){

        //TODO refer to the INSTANCE_ES of other types.
        if (td == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder(Arrays.class.getCanonicalName()).append(".asList(");
        final Iterator<TypeDescriptor> it = td.getSubTypes().iterator();
        while (it.hasNext()){
            sb.append(it.next().getOrdinal());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * This method will return a {@link String} representation of the
     * declaration and initialisation of a {@link MemberES} object for this
     * attribute. This is put in the velocity template when generating the meta
     * model.
     *
     * @param ad the {@link SingularAttributeDescriptor} to get the member for.
     * @param includeDeclaration if set to {@code true} the declaration of the
     * Member will be included in the string, if set to {@code false} only the
     * initialisation of the Member is included.
     * @return A {@link String} with the declaration and initialisation of a
     * {@link MemberES} object for this attribute.
     */
    private static String printMember(AttributeDescriptor ad, boolean includeDeclaration) {
        final String declarerJavaTypeCanonicalName = ad.getDeclaringTypeDescriptor().getJavaTypeCanonicalName();
        StringBuilder sb = new StringBuilder();
        if (includeDeclaration) {
            sb.append("private final ").append(MemberES.class.getCanonicalName()).append(" javaMember = new ").append(PropertyMember.class.getCanonicalName()).append("(");
        } else {
            sb.append("new ").append(PropertyMember.class.getCanonicalName()).append("(");
        }
        sb.append(declarerJavaTypeCanonicalName).append(".class, ");
        sb.append("\"").append(ad.getName()).append("\", ");
        sb.append("(").append(Getter.class.getCanonicalName()).append("<").append(declarerJavaTypeCanonicalName).append(",").append(ad.getJavaTypeCanonicalName()).append(">)").append(declarerJavaTypeCanonicalName).append("::").append(ad.getGetterMethodName()).append(", ");
        if (ad.getSetterMethodName() != null) {
            sb.append("(").append(Setter.class.getCanonicalName()).append("<").append(declarerJavaTypeCanonicalName).append(",").append(ad.getJavaTypeCanonicalName()).append(">)").append(declarerJavaTypeCanonicalName).append("::").append(ad.getSetterMethodName()).append(", ");
        } else {
            sb.append("null,");
        }
        sb.append(ad.getGetterMethod().getModifiers());
        if (includeDeclaration) {
            sb.append(");");
        } else {
            sb.append(")");
        }
        return sb.toString();
    }
}