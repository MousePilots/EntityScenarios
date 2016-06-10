/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.Authorization;
import org.mousepilots.es.test.constraints.annotations.AuthorizationContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class AuthorizationValidator implements ConstraintValidator<AuthorizationContainer, Request> {

    Authorization[] authorizations;

    @Override
    public void initialize(AuthorizationContainer constraintAnnotation) {
        authorizations = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for (Authorization auth : authorizations) {
            AuthorizationSingleValidator asv = new AuthorizationSingleValidator();
            asv.initialize(auth);
            if (!asv.isValid(value, context)) {
                return false;
            }
        }
        return true;
    }

}
