package org.mousepilots.es.test.domain;

import javax.persistence.MappedSuperclass;

/**
 * Mapped superclass that models a person.
 * @author Nicky Ernste
 * @version 1.0, 30-11-2015
 */
@MappedSuperclass
public abstract class Person extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private String infix;
    private String lastName;
    private int age;
    private String sex;

    public Person() {
    }

    public Person(String firstName, String infix, String lastName, int age,
            String sex) {
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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