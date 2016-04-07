package org.mousepilots.es.test.domain.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import org.mousepilots.es.test.domain.embeddables.Address;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
@Entity
public class Manager extends User<ManagerAccount> {
    private static final long serialVersionUID = 1L;


    @Embedded
    private Address managerAddress;

    public Address getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(Address managerAddress) {
        this.managerAddress = managerAddress;
    }
}