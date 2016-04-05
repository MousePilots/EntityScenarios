package org.mousepilots.es.maven.model.generator.model.attribute;

import java.lang.reflect.Method;
import java.util.Map;
import javax.persistence.metamodel.Bindable.BindableType;
import javax.persistence.metamodel.PluralAttribute.CollectionType;
import org.mousepilots.es.core.command.attribute.PluralAttributes;
import org.mousepilots.es.core.util.StringUtils;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.PluralAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public abstract class PluralAttributeDescriptor extends AttributeDescriptor{

    private final TypeDescriptor elementType;

    /**
     * Create a new instance of this class.
     * @param elementType the type of the elements for this plural attribute.
     * @param name the name of this plural attribute.
     * @param ordinal the ordinal of this plural attribute.
     * @param javaType the java type of this plural attribute.
     */
    public PluralAttributeDescriptor(TypeDescriptor elementType, String name, Class javaType,int ordinal) {
        super(name, javaType, ordinal);
        this.elementType = elementType;
    }

    /**
     * Get the {@link CollectionType} that this plural attribute represents.
     * @return the collection type.
     */
    public abstract CollectionType getCollectionType();

    @Override
    public String getGenericsString() {
        return "<" + StringUtils.append(
                        ", ", 
                        getDeclaringTypeDescriptor().getJavaTypeCanonicalName(),
                        getElementType().getJavaTypeCanonicalName()
                     ) + 
                ">";
    }

    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Map<String, String> cp2v = super.getConstructorParameterToValue();
        cp2v.put("elementTypeOrdinal", getElementType().getOrdinal().toString());
        return cp2v;
    }

    @Override
    public final String getProxyGetterDeclaration(){
        final Method getter = this.getGetterMethod(), setter = getSetterMethod();
        if(getter==null || setter==null){
            throw new IllegalStateException(getField() + " requires a getter and setter");
        }
        final String genericString = getter.toGenericString();
        final String methodDeclaration = 
                "\t@Override\n" + 
                "\t" + genericString.replace(getter.getDeclaringClass().getName() + ".", "") + "{\n" + 
                "\t\t return " + PluralAttributes.class.getCanonicalName() + ".get( this, " + this.getOrdinal() + ", super::" + getter.getName() + ", super::" + setter.getName() + ");\n" + 
                "\t}";
        
        return methodDeclaration;
    }

    @Override
    public final String getProxySetterDeclaration() {
        final Method getter = this.getGetterMethod(), setter = getSetterMethod();
        if(getter==null || setter==null){
            throw new IllegalStateException(getField() + " requires a getter and setter");
        }
        final String genericTypeString = "<" + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + "," + getter.getGenericReturnType().toString() + ">";
        System.out.println("genericTypeString for : " + getter + ": " + genericTypeString);
        final String genericString = setter.toGenericString();
        final String parameterName = setter.getParameters()[0].getName();
        final String methodDeclaration = 
                "\t@Override\n" + 
                "\t" + genericString.replace(setter.getDeclaringClass().getName() + ".", "").replace(")", " " + parameterName + ")") + "{\n" + 
                "\t\t" + PluralAttributes.class.getCanonicalName() + ".set( this, " + this.getOrdinal() + ", super::" + setter.getName() + ", " + parameterName + ");\n" + 
                "\t}";
        
        return methodDeclaration;

    }
    
    
    
    
    

    /**
     * Get the type of the elements for this plural attribute.
     * @return the element type.
     */
    public final TypeDescriptor getElementType() {
        return elementType;
    }

    /**
     * Gets the bindable type for this plural attribute.
     * @return the bindable type.
     */
    public final BindableType getBindableType(){
        return BindableType.PLURAL_ATTRIBUTE;
    }

    /**
     * Gets the bindable java type for this plural attribute.
     * @return the bindable java type.
     */
    public final Class getBindableJavaType(){
        return elementType.getJavaType();
    }
}