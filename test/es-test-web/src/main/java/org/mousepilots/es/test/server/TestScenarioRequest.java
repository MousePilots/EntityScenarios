/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import org.mousepilots.es.test.constraints.annotations.PatternES;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.test.constraints.annotations.Authorization;
import org.mousepilots.es.test.constraints.annotations.IllegalCreate;
import org.mousepilots.es.test.constraints.annotations.IllegalUpdate;
import org.mousepilots.es.test.constraints.annotations.NotNullES;
import org.mousepilots.es.test.constraints.annotations.SingleEntityCreate;
import org.mousepilots.es.test.domain.embeddables.Address;
import org.mousepilots.es.test.domain.entities.Manager;

/*
 * @author bhofsted
 */
@SingleEntityCreate(constrainedClasses = {Manager.class})
@IllegalCreate(constrainedClasses = {Manager.class})
@IllegalUpdate(constrainedClasses = {Manager.class})
@NotNullES(javaType = Manager.class, attribute = "userName", operation = CRUD.CREATE)
//@NotNullES(javaType = Manager.class, attribute = "serialVersionUID", operation = CRUD.CREATE)
@PatternES(attribute = "zipCode", javaType = Address.class, operation = CRUD.CREATE, regexp = "(\\d{4})\\s*([A-Za-z]{2})")
//@PatternES(javaType = Manager.class, attribute = "userName", operation = CRUD.CREATE, regexp = "(\\d{4})\\s*([A-Za-z]{2})")
@Authorization(javaType = Address.class, roles = "manager")
public class TestScenarioRequest extends Request {

    @Override
    public String getScenario() {
        return "testScenario";
    }
}
