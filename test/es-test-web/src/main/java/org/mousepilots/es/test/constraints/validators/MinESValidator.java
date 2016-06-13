/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.MinES;
import org.mousepilots.es.test.constraints.annotations.MinESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class MinESValidator implements ConstraintValidator<MinESContainer, Request> {

    MinES[] minES;

    @Override
    public void initialize(MinESContainer constraintAnnotation) {
        minES = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for (MinES min : minES) {
            MinESSingleValidator msv = new MinESSingleValidator();
            msv.initialize(min);
            if (!msv.isValid(value, context)) {
                return false;
            }
        }
        return true;
    }
}
