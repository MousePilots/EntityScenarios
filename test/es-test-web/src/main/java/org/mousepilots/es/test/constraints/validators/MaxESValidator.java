/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.MaxES;
import org.mousepilots.es.test.constraints.annotations.MaxESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class MaxESValidator  implements ConstraintValidator<MaxESContainer, Request> {
    
    MaxES[] maxES;

    @Override
    public void initialize(MaxESContainer constraintAnnotation) {
        maxES = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for (MaxES max : maxES) {
            MaxESSingleValidator msv = new MaxESSingleValidator();
            msv.initialize(max);
            if (!msv.isValid(value, context)) {
                return false;
            }
        }
        return true;
    }
}
