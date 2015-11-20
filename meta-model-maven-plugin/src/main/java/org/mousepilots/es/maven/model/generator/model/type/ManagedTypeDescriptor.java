package org.mousepilots.es.maven.model.generator.model.type;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Type;
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
 * @version 1.0, 18-11-2015
 */
public class ManagedTypeDescriptor extends TypeDescriptor {

    private SortedSet<AttributeDescriptor> attributes = new TreeSet<>();

    public ManagedTypeDescriptor(Type.PersistenceType persistenceType, String name, Class javaType, int ordinal) {
        super(persistenceType, name, javaType, ordinal);
    }

    public SortedSet<AttributeDescriptor> getAttributes() {
        return attributes;
    }

    public void setAttributes(SortedSet<AttributeDescriptor> attributes) {
        this.attributes = attributes;
    }

    public SortedSet<AttributeDescriptor> getDeclaredAttributes() {
        return retainDeclared(new TreeSet<>(getAttributes()));
    }


    public SortedSet<PluralAttributeDescriptor> getPluralAttributes(){
        return getAttributeDescriptors(PluralAttributeDescriptor.class);
    }

    public SortedSet<PluralAttributeDescriptor> getDeclaredPluralAttributes(){
        final SortedSet<PluralAttributeDescriptor> retval = getAttributeDescriptors(PluralAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    public SortedSet<SingularAttributeDescriptor> getSingularAttributes(){
        return getAttributeDescriptors(SingularAttributeDescriptor.class);
    }

    public SortedSet<SingularAttributeDescriptor> getDeclaredSingularAttributes(){
        final SortedSet<SingularAttributeDescriptor> retval = getAttributeDescriptors(SingularAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    public SortedSet<CollectionAttributeDescriptor> getCollectionAttributes(){
        return getAttributeDescriptors(CollectionAttributeDescriptor.class);
    }

    public SortedSet<CollectionAttributeDescriptor> getDeclaredCollectionAttributes(){
        final SortedSet<CollectionAttributeDescriptor> retval = getAttributeDescriptors(CollectionAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    public SortedSet<ListAttributeDescriptor> getListAttributes(){
        return getAttributeDescriptors(ListAttributeDescriptor.class);
    }

    public SortedSet<ListAttributeDescriptor> getDeclaredListAttributes(){
        final SortedSet<ListAttributeDescriptor> retval = getAttributeDescriptors(ListAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    public SortedSet<SetAttributeDescriptor> getSetAttributes(){
        return getAttributeDescriptors(SetAttributeDescriptor.class);
    }

    public SortedSet<SetAttributeDescriptor> getDeclaredSetAttributes(){
        final SortedSet<SetAttributeDescriptor> retval = getAttributeDescriptors(SetAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    public SortedSet<MapAttributeDescriptor> getMapAttributes(){
        return getAttributeDescriptors(MapAttributeDescriptor.class);
    }

    public SortedSet<MapAttributeDescriptor> getDeclaredMapAttributes(){
        final SortedSet<MapAttributeDescriptor> retval = getAttributeDescriptors(MapAttributeDescriptor.class);
        return retainDeclared(retval);
    }

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
}