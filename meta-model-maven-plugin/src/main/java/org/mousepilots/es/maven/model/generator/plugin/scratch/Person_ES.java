package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link Person} with persistence type: MAPPED_SUPERCLASS
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Person.class)
public class Person_ES extends BaseEntity_ES{

     public static volatile SingularAttributeES<Person,  java.lang.String> firstName;
     public static volatile SingularAttributeES<Person,  java.lang.String> lastName;
     public static volatile SingularAttributeES<Person,  java.lang.String> sex;
     public static volatile SingularAttributeES<Person,  java.lang.String> infix;
     public static volatile SingularAttributeES<Person,  java.lang.Integer> age;

    private static final org.mousepilots.es.core.model.MappedSuperclassTypeES<Person> INSTANCE_ES = new org.mousepilots.es.core.model.impl.MappedSuperclassTypeESImpl<>(
        35,
         -1,
        36,
         -1,
        new java.util.HashSet(java.util.Arrays.asList(35)),
        true,
        true,
        18,
        "Person",
        8,
        Person.class,
        javax.persistence.metamodel.Type.PersistenceType.MAPPED_SUPERCLASS,
        "Person",
        true,
        Person_.class,
        new java.util.HashSet(java.util.Arrays.asList(51, 52, 53, 54, 55)),
        2,
        java.util.Arrays.asList(7, 12)
        );

    public static org.mousepilots.es.core.model.TypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     firstName = (SingularAttributeES<Person, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(51);
     lastName = (SingularAttributeES<Person, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(52);
     sex = (SingularAttributeES<Person, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(53);
     infix = (SingularAttributeES<Person, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(54);
     age = (SingularAttributeES<Person, java.lang.Integer>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(55);
    }
}