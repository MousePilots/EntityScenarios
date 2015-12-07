package org.mousepilots.es.test.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Embeddable that models an address.
 * @author Nicky Ernste
 * @version 1.0, 1-12-2015
 */
@Embeddable
public class Address implements Serializable {

    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String country;

    public Address() {
    }

    public Address(String street, String houseNumber, String zipCode,
            String city, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}