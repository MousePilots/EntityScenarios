package org.mousepilots.es.test.model;

/**
 * This class represents an employee and is used for testing purposes.
 * @author Nicky Ernste
 * @version 1.0, 4-11-2015
 */
public class Employee extends Person {
    
    private String department;
    private float salary;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }    
}