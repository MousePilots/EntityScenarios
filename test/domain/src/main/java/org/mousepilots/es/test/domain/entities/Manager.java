package org.mousepilots.es.test.domain.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import org.mousepilots.es.test.domain.embeddables.Address;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
@Entity
public class Manager extends User<ManagerAccount> {
    private static final long serialVersionUID = 1L;

    @OneToOne
    private ManagerAccount account;

    @Embedded
    private Address managerAddress;

    @Override
    public ManagerAccount getAccount() {
        return super.getAccount(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAccount(ManagerAccount account) {
        super.setAccount(account); //To change body of generated methods, choose Tools | Templates.
    }

    public Address getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(Address managerAddress) {
        this.managerAddress = managerAddress;
    }
}