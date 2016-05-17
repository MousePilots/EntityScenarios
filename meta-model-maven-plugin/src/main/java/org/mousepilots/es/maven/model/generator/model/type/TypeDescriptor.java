package org.mousepilots.es.maven.model.generator.model.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.persistence.metamodel.Type.PersistenceType;
import org.mousepilots.es.core.model.HasValue;
import org.mousepilots.es.core.model.TypeES;
import org.mousepilots.es.core.model.impl.TypeESImpl;
import org.mousepilots.es.core.model.impl.hv.HasCharacter;
import org.mousepilots.es.core.model.impl.hv.HasCollection;
import org.mousepilots.es.core.model.impl.hv.HasDate;
import org.mousepilots.es.core.model.impl.hv.HasList;
import org.mousepilots.es.core.model.impl.hv.HasMap;
import org.mousepilots.es.core.model.impl.hv.HasNumber;
import org.mousepilots.es.core.model.impl.hv.HasSet;
import org.mousepilots.es.core.model.impl.hv.HasString;
import org.mousepilots.es.core.util.Maps;
import org.mousepilots.es.maven.model.generator.model.Descriptor;
import org.mousepilots.es.maven.model.generator.model.attribute.AttributeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.Type} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 14-12-2015
 */
public abstract class TypeDescriptor extends Descriptor<PersistenceType> {

    private static final Map<Class<? extends Object>, Class<? extends HasValue>> PRECONFIGURED_TYPE_TO_HASVALUE_TYPE = Maps.create(
        Arrays.asList(Character.class,      String.class,     Date.class,       Collection.class,     List.class,     Set.class,      Map.class,      Number.class),
        Arrays.asList(HasCharacter.class,   HasString.class,  HasDate.class,    HasCollection.class,  HasList.class,  HasSet.class,   HasMap.class,   HasNumber.class)
    );
    
    private static final Map<Class,TypeDescriptor> INSTANCES = new TreeMap<>((c1,c2) -> c1.getCanonicalName().compareTo(c2.getCanonicalName()));
    private HasValueDescriptor hasValueDescriptor;
    
    
    private Class<? extends HasValue> getPreconfiguredHasValueClass(){
        final Class javaType = getJavaType();
        final Set<Entry<Class<? extends Object>, Class<? extends HasValue>>> entrySet = PRECONFIGURED_TYPE_TO_HASVALUE_TYPE.entrySet();
        //try and exact match
        for(Entry<Class<? extends Object>, Class<? extends HasValue>> entry : entrySet){
            final Class<? extends Object> preconfiguredType = entry.getKey();
            if(preconfiguredType==javaType){
                return entry.getValue();
            }
        }
        //try supertype match
        for(Entry<Class<? extends Object>, Class<? extends HasValue>> entry : entrySet){
            final Class<? extends Object> preconfiguredType = entry.getKey();
            if(preconfiguredType.isAssignableFrom(javaType)){
                return entry.getValue();
            }
        }
        return null;
    }
    
    public final boolean requiredHasValueClassGeneration(){
        return getPreconfiguredHasValueClass()==null && getSuper()==null;
    }
    
    protected String getHasValueConstructorReference(){
        final Class<? extends HasValue> preconfiguredHasValueClass = getPreconfiguredHasValueClass();
        if(preconfiguredHasValueClass==null){
            final TypeDescriptor superTypeDescriptor = getSuper();
            if(superTypeDescriptor==null){
                return getHasValueDescriptor().getHasValueImplClassCanonicalName() + "::new";
            } else {
                return superTypeDescriptor.getHasValueConstructorReference();
            }
        } else {
            return preconfiguredHasValueClass.getCanonicalName() + "::new";
        }
        
    }
    
    
    
    

    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Map<String,String> cp2v = new HashMap<>();
        cp2v.put("ordinal", getOrdinal().toString());
        cp2v.put("javaType", getJavaTypeCanonicalName()+ ".class");
        cp2v.put("superTypeOrdinal", getSuperTypeOrdinal());
        cp2v.put("subTypeOrdinals", Descriptor.AsCollection(getSubTypes(), td -> td.getOrdinal().toString()));
        cp2v.put("hasValueConstructor", getHasValueConstructorReference());
        return cp2v;
    }
    
    public String getMetaModelExtensionClassCanonicalName(){
        return getJavaTypeCanonicalName().concat(DESCRIPTOR_CLASS_SUFFIX);
    }
    
    public final String getMetamodelExtensionClassDeclaration(){
        final StringBuilder sb = new StringBuilder("public class ").append(getJavaTypeSimpleName()).append(DESCRIPTOR_CLASS_SUFFIX);
        final TypeDescriptor superType = getSuper();
        if(superType!=null){
            sb.append(" extends ").append(superType.getJavaTypeCanonicalName()).append(DESCRIPTOR_CLASS_SUFFIX);
        }
        
        return sb.toString();
    }

    @Override
    public Class<? extends TypeES> getDeclaredClass() {
        return TypeES.class;
    }

    @Override
    protected Class<? extends TypeESImpl> getImplementationClass() {
        return TypeESImpl.class;
    }


    @Override
    protected String getGenericsString() {
        return "<" + getJavaTypeCanonicalName()+ ">";
    }

    @Override
    public String getDeclaredVariableName() {
        return "__TYPE";
    }
    

    /**
     * Create a new instance of this class.
     * @param name the name of this type.
     * @param ordinal the ordinal of this type.
     * @param javaType the java type of this type.
     * @param persistenceType the {@link PersistenceType} of this type.
     */
    public TypeDescriptor(
            PersistenceType persistenceType, 
            String name, 
            Class javaType,
            int ordinal) {
        super(persistenceType, name, javaType, ordinal);
        INSTANCES.put(javaType,this);
    }

    /**
     * @return Get all the current {@link TypeDescriptor}s.
     */
    public static Collection<TypeDescriptor> getAll() {
        return INSTANCES.values();
    }

    public HasValueDescriptor getHasValueDescriptor() {
        return hasValueDescriptor;
    }

    public void setHasValueDescriptor(HasValueDescriptor hasValueDescriptor) {
        this.hasValueDescriptor = hasValueDescriptor;
    }

    /**
     * The fully qualified name of the meta-model class this' meta-model class is a subclass of.
     * @return
     */
    public String getMetaModelSuperClassFullName(){
        final Descriptor<PersistenceType> superDescriptor = getSuperDescriptor();
        return superDescriptor == null ? null : superDescriptor.getDescriptorClassFullName();
    }

    /**
     * Get a specific instance of a descriptor.
     * @param javaType The class to get a descriptor of.
     * @return A {@link TypeDescriptor} of the specified class, or {@code null} if none was found.
     */
    public static TypeDescriptor getInstance(Class javaType){
        return INSTANCES.get(javaType);
    }

    /**
     * Searches the declared attributes for an attribute with the specified {@code name}.
     * If it is not found it will search in the super classes.
     * @param name the name of the attribute to get.
     * @return the {@link AttributeDescriptor} with the specified {@code name} or {@code null} if the attribute was not found.
     */
    public AttributeDescriptor getAttribute(String name){
        for(AttributeDescriptor ad : AttributeDescriptor.getAll()){
            if(ad.getName().equals(name)){
                if(ad.getDeclaringTypeDescriptor().getJavaType().isAssignableFrom(getJavaType())){
                    return ad;
                }
            }
        }
        return null;
    }

    /**
     * Get the super descriptor if this descriptor if any.
     * @return the super descriptor of this type, or {@code null} if this type has no super descriptor.
     */
    public TypeDescriptor getSuper(){
        final Class superclass = getJavaType().getSuperclass();
        return superclass==null ? null : getInstance(superclass);
    }
    
    public String getSuperTypeOrdinal(){
        final TypeDescriptor superType = getSuper();
        return superType==null ? "null" : superType.getOrdinal().toString();
        
    }

    /**
     * Get a specific instance of a descriptor.
     * @param <T> A type that extends {@link TypeDescriptor}.
     * @param javaClass The class to get the descriptor of.
     * @param typeDescriptorClass The type of descriptor you wish to have.
     * @return A descriptor of the specified {@code typeDescriptorClass}, or {@code null} if none was found.
     */
    public static <T extends TypeDescriptor> T getInstance(Class javaClass, Class<T> typeDescriptorClass){
        TypeDescriptor td = getInstance(javaClass);
        if (td != null && typeDescriptorClass.isAssignableFrom(td.getClass())) {
            return (T) td;
        } else {
            return null;
        }
    }

    /**
     * Check if this type is instantiable.
     * @return {@code true} if this type can be instantiated, or {@code false} otherwise.
     */
    public boolean isInstantiable(){
        final Class javaType = getJavaType();
        if(Modifier.isAbstract(javaType.getModifiers())){
            return false;
        } else {
            for(Constructor c : javaType.getConstructors()){
                if(c.getParameterCount()==0){
                    final int modifiers = c.getModifiers();
                    return Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers);
                }
            }
            return false;
        }
    }
    
    public String getJavaTypeConstructorReference(){
        if(isInstantiable()){
            return this.getJavaTypeCanonicalName() + "::new";
        } else {
            return "null";
        }
    }

    public final Set<TypeDescriptor> getSubTypes() {
        Set<TypeDescriptor> retval = new HashSet<>();
        for(TypeDescriptor td : TypeDescriptor.getAll()){
            if(getJavaType().isAssignableFrom(td.getJavaType())){
                retval.add(td);
            }
        }
        return retval;
    }

}