/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author AP34WV
 */
@Entity
public class Employee extends User<Account>{
    
    @ManyToOne
    private Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
