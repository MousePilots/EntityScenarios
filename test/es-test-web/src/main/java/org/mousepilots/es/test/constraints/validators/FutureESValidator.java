/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import org.mousepilots.es.test.constraints.annotations.FutureESContainer;
import org.mousepilots.es.test.constraints.annotations.FutureES;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class FutureESValidator implements ConstraintValidator<FutureESContainer, Request> {
    
    FutureES[] future;
    
    @Override
    public void initialize(FutureESContainer constraintAnnotation) {
        future = constraintAnnotation.value();
    }
    
    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for(FutureES fe : future){
            FutureESSingleValidator fsv = new FutureESSingleValidator();
            fsv.initialize(fe);
            if(!fsv.isValid(value, context)){
                return false;
            }
        }
        return true;
    }
}