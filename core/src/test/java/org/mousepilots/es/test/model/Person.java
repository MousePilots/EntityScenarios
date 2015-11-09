package org.mousepilots.es.test.model;

/**
 * This class represents a person and is used for testing purposes.
 * @author Nicky Ernste
 * @version 1.0, 4-11-2015
 */
public class Person {
    
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }    
}