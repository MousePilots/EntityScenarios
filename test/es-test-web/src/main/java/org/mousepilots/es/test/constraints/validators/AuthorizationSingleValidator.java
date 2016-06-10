/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import java.util.Arrays;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.command.Command;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.model.impl.AbstractMetamodelES;
import org.mousepilots.es.test.constraints.MessageBuilder;
import org.mousepilots.es.test.constraints.annotations.Authorization;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class AuthorizationSingleValidator implements ConstraintValidator<Authorization, Request> {

//    @Inject
//    ServerContext serverContext;

    private ManagedTypeES<?> managedType;

    private Collection<String> roles;

    private Collection<CRUD> operation;

    @Override
    public void initialize(Authorization constraintAnnotation) {
        managedType = AbstractMetamodelES.getInstance().managedType(constraintAnnotation.javaType());
        roles = Arrays.asList(constraintAnnotation.roles());
        operation = Arrays.asList(constraintAnnotation.operation());
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext cvc) {
        for (Command command : value.getCommands()) {
            if (command.getType() == managedType && operation.contains(command.getOperation())) {
                if (!value.getServerContext().hasRoleIn(roles)) {
                    new MessageBuilder().buildMessage(cvc, "", managedType.getJavaType().getSimpleName());
                    return false;
                }
            }
        }
        return true;
    }
}
