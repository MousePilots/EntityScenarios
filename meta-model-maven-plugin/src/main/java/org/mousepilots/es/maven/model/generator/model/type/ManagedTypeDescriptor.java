package org.mousepilots.es.maven.model.generator.model.type;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.ManagedTypeESImpl;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.util.StringUtils;
import org.mousepilots.es.maven.model.generator.model.AssociationDescriptor;
import org.mousepilots.es.maven.model.generator.model.Descriptor;
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

    
    private static final String PROXY_SUFFIX = "_ES_Proxy";
    private SortedSet<AttributeDescriptor> declaredAttributes = new TreeSet<>();
    private final Class<?> metaModelClass;

    /**
     * Create a new instance of this class.
     * @param metaModelClass the JPA meta model class that models this type.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     */
    public ManagedTypeDescriptor(
            Class<?> metaModelClass,
            PersistenceType persistenceType, 
            String name, 
            Class javaType,
            int ordinal){
        super(persistenceType, name, javaType, ordinal);
        this.metaModelClass = metaModelClass;
    }


    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return ManagedTypeES.class;
    }
    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return ManagedTypeESImpl.class;
    }
    
    private Set<AssociationDescriptor> getAssociations(){
        HashSet<AssociationDescriptor> retval = new HashSet<>();
        for(AttributeDescriptor ad : getAttributes()){
            for(AssociationTypeES at : AssociationTypeES.values()){
                final AssociationDescriptor a = ad.getAssociation(at);
                if(a!=null){
                    retval.add(a);
                }
            }
        }
        return retval;
    }

    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Map<String, String> cp2v = super.getConstructorParameterToValue();
        cp2v.put("metamodelClass",      getMetaModelClass().getSimpleName() + ".class");
        cp2v.put("javaTypeConstructor", getJavaTypeConstructorReference());
        cp2v.put("proxyType",           getProxyClass());
        cp2v.put("proxyTypeConstructor",getProxyConstructorReferenence());
        cp2v.put("attributeOrdinals",   Descriptor.AsCollection(getAttributes(), a -> a.getOrdinal().toString()));
        cp2v.put("associationOrdinals", Descriptor.AsCollection(getAssociations(), a -> a.getOrdinal().toString()));
        cp2v.put("declaredAttributes",  Descriptor.AsCollection(getDeclaredAttributes(), ad -> ad.getName()));
        return cp2v;
    }
    
    
    public final Class<?> getMetaModelClass() {
        return metaModelClass;
    }    

    public String getProxyClass(){
        return isInstantiable() ? getJavaTypeCanonicalName() + PROXY_SUFFIX  + ".class": "null";
    }
    
    
    public String getProxyClassCanonicalName(){
        return isInstantiable() ? getJavaTypeCanonicalName() + PROXY_SUFFIX : "null";
    }

    public String getProxyClassSimpleName(){
        return isInstantiable() ? getJavaTypeSimpleName() + PROXY_SUFFIX : "null";
    }
    
    public String getProxyConstructorReferenence(){
        return isInstantiable() ? getProxyClassCanonicalName() + "::new" : "null";
    }
    /**
     * Get the declaredAttributes of this managed type.
     * @return a set of declaredAttributes.
     */
    public final SortedSet<AttributeDescriptor> getAttributes(){
        final SortedSet<AttributeDescriptor> allAttributes = new TreeSet<>();
        allAttributes.addAll(declaredAttributes);
        for(TypeDescriptor td = getSuper(); td!=null && td instanceof ManagedTypeDescriptor; td=td.getSuper()){
            ManagedTypeDescriptor superTypeDescriptor = (ManagedTypeDescriptor) td;
            for(AttributeDescriptor superAttributeDescriptor : superTypeDescriptor.getDeclaredAttributes()){
                boolean addSuperAttributeDescriptor=true;
                for(AttributeDescriptor added : allAttributes){
                    if(added.getName().equals(superAttributeDescriptor.getName())){
                        addSuperAttributeDescriptor=false;
                        break;
                    }
                }
                if(addSuperAttributeDescriptor){
                    allAttributes.add(superAttributeDescriptor);
                }
            }
        }
        return allAttributes;
    }

    /**
     * Set the declaredAttributes of this managed type.
     * @param attributes a set of declaredAttributes.
     */
    public final void setDeclaredAttributes(SortedSet<AttributeDescriptor> attributes) {
        this.declaredAttributes = attributes;
    }

    /**
     * Get the declaredAttributes declared by this managed type.
     * @return a set of declared declaredAttributes, or an empty set if there are no declared declaredAttributes.
     */
    public final SortedSet<AttributeDescriptor> getDeclaredAttributes() {
        return declaredAttributes;
    }

    /**
     * Get all multi-valued declaredAttributes (Collection-, Set-, List-, and Map-valued declaredAttributes) of this managed type.
     * @return a set of plural declaredAttributes, or an empty set if there are no plural declaredAttributes.
     */
    public SortedSet<PluralAttributeDescriptor> getPluralAttributes(){
        return getAttributeDescriptors(PluralAttributeDescriptor.class);
    }

    /**
     * Get all multi-valued declaredAttributes (Collection-, Set-, List-, and Map-valued declaredAttributes) declared by this managed type
     * @return a set of declared plural declaredAttributes, or an empty set if there are no declared declaredAttributes.
     */
    public SortedSet<PluralAttributeDescriptor> getDeclaredPluralAttributes(){
        final SortedSet<PluralAttributeDescriptor> retval = getAttributeDescriptors(PluralAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the single-valued declaredAttributes of this managed type.
     * @return a set of singular declaredAttributes, or an empty set if there are no singular declaredAttributes.
     */
    public SortedSet<SingularAttributeDescriptor> getSingularAttributes(){
        return getAttributeDescriptors(SingularAttributeDescriptor.class);
    }

    /**
     * Get the single-valued declaredAttributes declared by this managed type.
     * @return a set of declared singular declaredAttributes, or an empty set if there are no declared singular declaredAttributes.
     */
    public SortedSet<SingularAttributeDescriptor> getDeclaredSingularAttributes(){
        final SortedSet<SingularAttributeDescriptor> retval = getAttributeDescriptors(SingularAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Collection-valued declaredAttributes of thiss managed type.
     * @return a set of collection declaredAttributes, or an empty set if there are no collection declaredAttributes.
     */
    public SortedSet<CollectionAttributeDescriptor> getCollectionAttributes(){
        return getAttributeDescriptors(CollectionAttributeDescriptor.class);
    }

    /**
     * Get the Collection-valued declaredAttributes declared by this managed type.
     * @return a set of declared collection declaredAttributes, or an empty set if there are no declared collection declaredAttributes.
     */
    public SortedSet<CollectionAttributeDescriptor> getDeclaredCollectionAttributes(){
        final SortedSet<CollectionAttributeDescriptor> retval = getAttributeDescriptors(CollectionAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the List-valued declaredAttributes of this managed type.
     * @return a set of list declaredAttributes, or an empty set if there are no list declaredAttributes.
     */
    public SortedSet<ListAttributeDescriptor> getListAttributes(){
        return getAttributeDescriptors(ListAttributeDescriptor.class);
    }

    /**
     * Get the List-valued declaredAttributes declared by this managed type.
     * @return a set of declared list declaredAttributes, or an empty set if there are no declared list declaredAttributes.
     */
    public SortedSet<ListAttributeDescriptor> getDeclaredListAttributes(){
        final SortedSet<ListAttributeDescriptor> retval = getAttributeDescriptors(ListAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Set-valued declaredAttributes of the managed type.
     * @return a set of set declaredAttributes, or an empty set if there a no set declaredAttributes.
     */
    public SortedSet<SetAttributeDescriptor> getSetAttributes(){
        return getAttributeDescriptors(SetAttributeDescriptor.class);
    }

    /**
     * Get the Set-valued declaredAttributes declared by the managed type.
     * @return a set of declared set declaredAttributes, or an empty set if there a no declared set declaredAttributes.
     */
    public SortedSet<SetAttributeDescriptor> getDeclaredSetAttributes(){
        final SortedSet<SetAttributeDescriptor> retval = getAttributeDescriptors(SetAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Get the Map-valued declaredAttributes of the managed type.
     * @return a list of map declaredAttributes, or an empty set if there a no map declaredAttributes.
     */
    public SortedSet<MapAttributeDescriptor> getMapAttributes(){
        return getAttributeDescriptors(MapAttributeDescriptor.class);
    }

    /**
     * Get the Map-valued declaredAttributes declared by the managed type.
     * @return a list of declared map declaredAttributes, or an empty set if there a no declared map declaredAttributes.
     */
    public SortedSet<MapAttributeDescriptor> getDeclaredMapAttributes(){
        final SortedSet<MapAttributeDescriptor> retval = getAttributeDescriptors(MapAttributeDescriptor.class);
        return retainDeclared(retval);
    }

    /**
     * Retain only the declared declaredAttributes from a set of declaredAttributes.
     * @param <T> the type of declaredAttributes to get the declared declaredAttributes from.
     * @param retval a set of declaredAttributes to get the declared declaredAttributes from.
     * @return a set that contains only the declaredAttributes that are declared of the specified type.
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