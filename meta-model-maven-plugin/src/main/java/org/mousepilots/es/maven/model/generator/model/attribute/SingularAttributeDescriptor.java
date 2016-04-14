package org.mousepilots.es.maven.model.generator.model.attribute;

import java.lang.reflect.Method;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.metamodel.Bindable.BindableType;
import javax.validation.constraints.NotNull;
import org.mousepilots.es.core.command.attribute.SingularAttributes;
import org.mousepilots.es.core.model.AssociationTypeES;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.Generator;
import org.mousepilots.es.core.model.SingularAttributeES;
import org.mousepilots.es.core.model.impl.AttributeESImpl;
import org.mousepilots.es.core.model.impl.DescendingLongGenerator;
import org.mousepilots.es.core.model.impl.SingularAttributeESImpl;
import org.mousepilots.es.maven.model.generator.model.type.TypeDescriptor;

/**
 * Descriptor of the {@link javax.persistence.metamodel.SingularAttribute} of JPA.
 * @author Nicky Ernste
 * @version 1.0, 25-11-2015
 */
public final class SingularAttributeDescriptor extends AttributeDescriptor {

    private Class<? extends Generator> generatorClass;
    
    /**
     * Create a new instance of this class.
     * @param name the name of this plural attribute.
     * @param ordinal the ordinal of this plural attribute.
     * @param javaType the java type of this plural attribute.
     */
    public SingularAttributeDescriptor(
            String name, 
            Class javaType,
            int ordinal) {
        super(name, javaType, ordinal);
    }


    public Class<? extends Generator> getGeneratorClass(){
        if(isGenerated()){
            if(generatorClass==null){
                final Class javaType = getJavaType();
                if(javaType==Long.class || javaType==long.class){
                    generatorClass = DescendingLongGenerator.class;
                } else {
                    throw new IllegalStateException("no generator condigured for " + this);
                }
            } 
        }
        return generatorClass;
    }
    
    public Boolean isId(){
        return this.getAnnotation(Id.class)!=null;
    }

    public void setGeneratorClass(Class<? extends Generator> generatorClass) {
        this.generatorClass = generatorClass;
    }

    /**
     * Get the {@link BindableType} for this singular attribute.
     * @return the bindable type.
     */
    public BindableType getBindableType(){
        return BindableType.SINGULAR_ATTRIBUTE;
    }
    
    public Boolean isGenerated(){
        return getAnnotation(GeneratedValue.class)!=null;
    }

    public Boolean isVersion(){
        return getAnnotation(Version.class)!=null;
    }
    
    /**
     * Get the bindable java type for this singular attribute.
     * @return the bindable java type.
     */
    public Class getBindableJavaType(){
        return getJavaType();
    }

    /**
     * Check if this singular attribute is an optional attribute.
     * If this attribute is part of the id or if this attribute is not nullable it is considered to be not optional.
     * @return {@code true} if this attribute is considered to be optional, {@code false} otherwise.
     */
    public Boolean isOptional(){
        final Column column = getAnnotation(Column.class);
        final NotNull notNull = getAnnotation(NotNull.class);
        if(!isId() || (column != null && !column.nullable()) || notNull!=null) {
            return false;
        }
        return true;
    }

    @Override
    public Class<? extends AttributeES> getDeclaredClass() {
        return SingularAttributeES.class;
    }

    @Override
    public Class<? extends AttributeESImpl> getImplementationClass() {
        return SingularAttributeESImpl.class;
    }

    @Override
    public String getGenericsString(){
        return "<" 
                + getDeclaringTypeDescriptor().getJavaTypeCanonicalName() + ","
                + getJavaTypeCanonicalName() + 
                ">";
    }
    
    @Override
    protected Map<String, String> getConstructorParameterToValue() {
        final Class<? extends Generator> configuredGeneratorClass = getGeneratorClass();
        final String newGenerator = configuredGeneratorClass==null ? "null" : "new " + configuredGeneratorClass.getCanonicalName() + "()";
        final Map<String, String> cp2v = super.getConstructorParameterToValue();
        cp2v.put("valueAssociation", getAssociationInstantiation(AssociationTypeES.VALUE));
        cp2v.put("generator", newGenerator);
        cp2v.put("id", isId().toString());
        cp2v.put("version", isVersion().toString());
        cp2v.put("optional", isOptional().toString());
        return cp2v;
    }
    
    @Override
    public final String getProxySetterDeclaration(TypeDescriptor hasAttribute) {
        final Method setter = getSetterMethod();
        if(setter==null){
            return null;
        } else {
            final String parameterName = setter.getParameters()[0].getName();
            final GenericMethodInfo methodInfo = new GenericMethodInfo(hasAttribute.getJavaType(), setter);
            final String methodDeclaration = 
                    "\t@Override\n" + 
                    "\t public void " + setter.getName() + "(" + methodInfo.getParameterTypes().get(0) + " " + parameterName + "){\n" + 
                    "\t\t" + SingularAttributes.class.getCanonicalName() + ".set( this, " + this.getOrdinal() + ", super::" + setter.getName() + ", " + parameterName + ");\n" + 
                    "\t}";

            return methodDeclaration;
        }
    }
    
    @Override
    public <I, O> O accept(AttributeDescriptorVisitor<I, O> visitor, I arg) {
        return visitor.visit(this, arg);
    }
    
}