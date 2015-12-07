package org.mousepilots.es.core.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The StaticMetamodel annotation specifies that the class
 * is a metamodel class that represents the entity, mapped
 * superclass, or embeddable class designated by the value
 * element.
 * @author Roy Cleven
 * @version 1.0, 19-10-2015
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StaticMetamodelES {

    /**
     * Class being modeled by the annotated class.
     * @return The class that is modeled by the annotated class.
     */
    Class<?> value();
}