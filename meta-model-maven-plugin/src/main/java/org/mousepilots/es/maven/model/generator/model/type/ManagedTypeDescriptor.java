package org.mousepilots.es.maven.model.generator.model.type;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.CollectionAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.ListAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.MapAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.PluralAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SetAttributeDescriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.SingularAttributeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.ManagedType} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public class ManagedTypeDescriptor extends TypeDescriptor {

    private SortedSet<AttributeDescriptor> attributes = new TreeSet<>();

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     */
    public ManagedTypeDescriptor(Class<?> metaModelClass,
            PersistenceType persistenceType, String name, Class javaType,
            int ordinal) {
        super(metaModelClass, persistenceType, name, javaType, ordinal);
    }

    /**
     * Get the attributes of this managed type.
     * @return a set of attributes.
     */
    public SortedSet<AttributeDescriptor> getAttributes() {
        return attributes;
    }

    /**
     * Set the attributes of this managed type.
     * @param attributes a set of attributes.
     */
    public void setAttributes(SortedSet<AttributeDescriptor> attributes) {
        this.attributes = attributes;
    }

    /**
     * Get the attributes declared by this managed type.
     * @return a set of declared attributes, or an empty set if there are no declared attributes.
     */
    public SortedSet<AttributeDescriptor> getDeclaredAttributes() {
        return retainDeclared(new TreeSet<>(getAttributes()));
    }

    /**
     * Get all multi-valued attributes (Collection-, Set-, List-, and Map-valued attributes) of this managed type.
     * @return a set of plural attributes, or an empty set if there are no plural attributes.
     */
    public SortedSet<PluralAttributeDescriptor> getPluralAttributes(){
        return getAttributeDescriptors(PluralAttributeDescriptor.class);
    }

    /**
     * Get all multi-valued attributes (Collection-, Set-, List-, and Map-valued attributes) declared by this managed type
     * @return a set of declared plural attributes, or an empty set if there are no declared attributes.
     */
    public SortedSet<PluralAttributeDescriptor> getDeclaredPluralAttributes(){
        final SortedSet<PluralAttributeDescriptor> retval = getAttributeDescriptors(PluralAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the single-valued attributes of this managed type.
     * @return a set of singular attributes, or an empty set if there are no singular attributes.
     */
    public SortedSet<SingularAttributeDescriptor> getSingularAttributes(){
        return getAttributeDescriptors(SingularAttributeDescriptor.class);
    }

    /**
     * Get the single-valued attributes declared by this managed type.
     * @return a set of declared singular attributes, or an empty set if there are no declared singular attributes.
     */
    public SortedSet<SingularAttributeDescriptor> getDeclaredSingularAttributes(){
        final SortedSet<SingularAttributeDescriptor> retval = getAttributeDescriptors(SingularAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Collection-valued attributes of thiss managed type.
     * @return a set of collection attributes, or an empty set if there are no collection attributes.
     */
    public SortedSet<CollectionAttributeDescriptor> getCollectionAttributes(){
        return getAttributeDescriptors(CollectionAttributeDescriptor.class);
    }

    /**
     * Get the Collection-valued attributes declared by this managed type.
     * @return a set of declared collection attributes, or an empty set if there are no declared collection attributes.
     */
    public SortedSet<CollectionAttributeDescriptor> getDeclaredCollectionAttributes(){
        final SortedSet<CollectionAttributeDescriptor> retval = getAttributeDescriptors(CollectionAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the List-valued attributes of this managed type.
     * @return a set of list attributes, or an empty set if there are no list attributes.
     */
    public SortedSet<ListAttributeDescriptor> getListAttributes(){
        return getAttributeDescriptors(ListAttributeDescriptor.class);
    }

    /**
     * Get the List-valued attributes declared by this managed type.
     * @return a set of declared list attributes, or an empty set if there are no declared list attributes.
     */
    public SortedSet<ListAttributeDescriptor> getDeclaredListAttributes(){
        final SortedSet<ListAttributeDescriptor> retval = getAttributeDescriptors(ListAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Set-valued attributes of the managed type.
     * @return a set of set attributes, or an empty set if there a no set attributes.
     */
    public SortedSet<SetAttributeDescriptor> getSetAttributes(){
        return getAttributeDescriptors(SetAttributeDescriptor.class);
    }

    /**
     * Get the Set-valued attributes declared by the managed type.
     * @return a set of declared set attributes, or an empty set if there a no declared set attributes.
     */
    public SortedSet<SetAttributeDescriptor> getDeclaredSetAttributes(){
        final SortedSet<SetAttributeDescriptor> retval = getAttributeDescriptors(SetAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Map-valued attributes of the managed type.
     * @return a list of map attributes, or an empty set if there a no map attributes.
     */
    public SortedSet<MapAttributeDescriptor> getMapAttributes(){
        return getAttributeDescriptors(MapAttributeDescriptor.class);
    }

    /**
     * Get the Map-valued attributes declared by the managed type.
     * @return a list of declared map attributes, or an empty set if there a no declared map attributes.
     */
    public SortedSet<MapAttributeDescriptor> getDeclaredMapAttributes(){
        final SortedSet<MapAttributeDescriptor> retval = getAttributeDescriptors(MapAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Retain only the declared attributes from a set of attributes.
     * @param <T> the type of attributes to get the declared attributes from.
     * @param retval a set of attributes to get the declared attributes from.
     * @return a set that contains only the attributes that are declared of the specified type.
     */
    private <T extends AttributeDescriptor> SortedSet<T> retainDeclared(final SortedSet<T> retval) {
        for(Iterator<T> i = retval.iterator(); i.hasNext(); ){
            final T ad = i.next();
            if(ad.getDeclaringTypeDescriptor()!=this){
                i.remove();
            }
        }
        return retval;
    }

    /**
     * @param <T> A type that extends {@link Annotation}.
     * @param annotationClass The annotation to look for.
     * @return the first attribute found which is annotated with {@code annotationClass}
     */
    public <T extends Annotation> AttributeDescriptor getAttributeAnnotatedWith(Class<T> annotationClass){
        for(AttributeDescriptor ad : getAttributes()){
            final T annotation = ad.getAnnotation(annotationClass);
            if(annotation!=null){
                return ad;
            }
        }
        return null;
    }

    /**
     * Get all the {@link AttributeDescriptor}s for a certain class.
     * @param <T> A type that extends {@link AttributeDescriptor}.
     * @param clazz The class to get the descriptors for.
     * @return A sorted set with all the found {@link AttributeDescriptor}s.
     */
    public <T extends AttributeDescriptor> SortedSet<T> getAttributeDescriptors(Class<T> clazz){
        SortedSet<T> returnSet = new TreeSet<>();
        for (AttributeDescriptor attDescriptor : getAttributes()){
            if (clazz.isAssignableFrom(attDescriptor.getClass())) {
                returnSet.add((T)attDescriptor);
            }
        }
        return returnSet;
    }

    @Override
    public final AttributeDescriptor getAttribute(String name){
        for (AttributeDescriptor attribute : getDeclaredAttributes()){
            if (attribute.getName().equals(name)) {
                return attribute;
            }
        }
        TypeDescriptor superType = getSuper();
        if (superType == null) {
            return null;
        } else {
            return superType.getAttribute(name);
        }
    }
}