package org.mousepilots.es.test.domain;

import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * Mapped superclass that models a person.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@MappedSuperclass
public abstract class Person extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private String infix;
    private String lastName;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Person() {
    }

    public Person(String firstName, String infix, String lastName, int age,  Gender sex) {
        this.firstName = firstName;
        this.infix = infix;
        this.lastName = lastName;
        this.age = age;
        this.gender = sex;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        hash = 23 * hash + this.age;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        return true;
    }
}