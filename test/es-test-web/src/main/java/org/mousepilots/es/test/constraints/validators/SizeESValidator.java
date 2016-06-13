/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import org.mousepilots.es.test.constraints.annotations.SizeES;
import org.mousepilots.es.test.constraints.annotations.SizeESContainer;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class SizeESValidator implements ConstraintValidator<SizeESContainer, Request> {
    SizeES[] sizeES;

    @Override
    public void initialize(SizeESContainer constraintAnnotation) {
        sizeES = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
       for (SizeES size : sizeES) {
            SizeESSingleValidator ssv = new SizeESSingleValidator();
            ssv.initialize(size);
            if (!ssv.isValid(value, context)) {
                return false;
            }
        }
        return true;
    }
}
