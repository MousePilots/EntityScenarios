package org.mousepilots.es.test.model;

import java.util.List;

/**
 * This class represents a manager and is for testing purposes.
 * @author Nicky Ernste
 * @version 1.0, 4-11-2015
 */
public class Manager extends Employee {
    
    private List<Employee> subordinates;

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }
    
    public Manager(List<Employee> subordinates){
        this.subordinates = subordinates;
    }
}