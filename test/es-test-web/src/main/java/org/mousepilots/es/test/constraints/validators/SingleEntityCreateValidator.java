/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.test.constraints.MessageBuilder;
import org.mousepilots.es.test.constraints.annotations.SingleEntityCreate;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class SingleEntityCreateValidator implements ConstraintValidator<SingleEntityCreate, Request> {

    private Set<Class> constrainedClasses;
    
    @Override
    public void initialize(SingleEntityCreate constraint) {
        this.constrainedClasses = new HashSet(Arrays.asList(constraint.constrainedClasses()));
    }

    @Override
    public boolean isValid(Request t, ConstraintValidatorContext cvc) {
        Set<ManagedTypeES> creationsSeen = new HashSet<>();
        for(Command command : t.getCommands()){
            final ManagedTypeES type = command.getType();
            if(command.getOperation()==CRUD.CREATE && constrainedClasses.contains(type.getJavaType())){
                if(creationsSeen.contains(type)){
                    new MessageBuilder().buildMessage(cvc, "", type.getJavaType().getSimpleName());
                    return false;
                } else {
                    creationsSeen.add(type);
                }
            }
        }
        return true;
    }
}
