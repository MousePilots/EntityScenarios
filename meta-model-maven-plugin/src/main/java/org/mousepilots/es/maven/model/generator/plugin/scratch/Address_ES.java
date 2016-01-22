package org.mousepilots.es.maven.model.generator.plugin.scratch;

import org.mousepilots.es.core.model.SingularAttributeES;

/**
 * Models class {@link Address} with persistence type: EMBEDDABLE
 */
@javax.annotation.Generated(value = "meta-model-maven-plugin_0.1-SNAPSHOT", date = "Fri Jan 08 13:28:36 CET 2016")
//@javax.persistence.metamodel.StaticMetamodel(Address.class)
public class Address_ES {

     public static volatile SingularAttributeES<Address,  java.lang.String> zipCode;
     public static volatile SingularAttributeES<Address,  java.lang.String> country;
     public static volatile SingularAttributeES<Address,  java.lang.String> city;
     public static volatile SingularAttributeES<Address,  java.lang.String> street;
     public static volatile SingularAttributeES<Address,  java.lang.String> houseNumber;

    private static final org.mousepilots.es.core.model.EmbeddableTypeES<Address> INSTANCE_ES = new org.mousepilots.es.core.model.impl.EmbeddableTypeESImpl<>(
        "Address",
        1,
        Address.class,
        javax.persistence.metamodel.Type.PersistenceType.EMBEDDABLE,
        "Address",
        true,
        Address_.class,
        new java.util.HashSet(java.util.Arrays.asList(30, 31, 32, 33, 34)),
        -1,
        java.util.Arrays.asList()
        );

    public static org.mousepilots.es.core.model.EmbeddableTypeES getInstance() {
        return INSTANCE_ES;
    }

    static {
     zipCode = (SingularAttributeES<Address, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(30);
     country = (SingularAttributeES<Address, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(31);
     city = (SingularAttributeES<Address, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(32);
     street = (SingularAttributeES<Address, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(33);
     houseNumber = (SingularAttributeES<Address, java.lang.String>) org.mousepilots.es.core.model.impl.AbstractMetaModelES.getInstance().getAttribute(34);
    }
}