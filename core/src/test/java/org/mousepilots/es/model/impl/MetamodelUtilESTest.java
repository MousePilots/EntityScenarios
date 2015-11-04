package org.mousepilots.es.model.impl;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.mousepilots.es.model.TypeES;
import org.mousepilots.es.test.model.Employee;
import org.mousepilots.es.test.model.Manager;
import org.mousepilots.es.test.model.Person;

/**
 * Test the MetamodelUtilES utility class.
 * @author Nicky Ernste
 * @version 1.0, 4-11-2015
 */
public class MetamodelUtilESTest {

    /**
     * Test of getSuperTypes method, of class MetamodelUtilES.
     */
    //TODO Uncomment this test when the implementation of the getSuperTypes method is completed.
    //@Test
    public void testGetSuperTypes() {
        Collection<TypeES<? super Manager>> superTypes = MetamodelUtilES.getSuperTypes(Manager.class);
        Assert.assertEquals("There are not 2 super types", 2, superTypes.size());
        TypeES<? super Manager>[] superTypeArr = (TypeES<? super Manager>[])superTypes.toArray();
        Assert.assertEquals("First super type is not Employee", "Employee", superTypeArr[0].getJavaClassName());
        Assert.assertEquals("Second super type is not Person", "Person", superTypeArr[1].getJavaClassName());
    }

    /**
     * Test of createInstance method, of class MetamodelUtilES.
     */
    @Test
    public void testCreateInstance() {
        //Positive Tests.
        Employee employee = MetamodelUtilES.createInstance(Employee.class);
        Assert.assertNotNull("The employee was not created", employee);
        employee.setName("Piet Pieterson");
        employee.setAge(27);
        employee.setSex("Male");
        employee.setDepartment("Finances");
        employee.setSalary(4369.59F);
        Assert.assertEquals("Name is not Piet Pieterson", "Piet Pieterson", employee.getName());
        Assert.assertEquals("Age is not 27", 27, employee.getAge());
        Assert.assertEquals("Sex is not Male", "Male", employee.getSex());
        Assert.assertEquals("Department is not Finances", "Finances", employee.getDepartment());
        Assert.assertEquals("Salary is not 4369.59", 4369.59F, employee.getSalary(), 1.0D);
        
        //Negative Tests.
        Manager manager = MetamodelUtilES.createInstance(Manager.class);
        Assert.assertNull("Manager was instantiated", manager);
    }

    /**
     * Test of isInstantiable method, of class MetamodelUtilES.
     */
    @Test
    public void testIsInstantiable() {
        //Positive Tests.
        Assert.assertTrue("Person is not instantiable", MetamodelUtilES.isInstantiable(Person.class));
        Assert.assertTrue("Employee is not instantiable", MetamodelUtilES.isInstantiable(Employee.class));
        
        //Negative Tests.
        Assert.assertFalse("Manager is instantiable", MetamodelUtilES.isInstantiable(Manager.class));
    }    
}