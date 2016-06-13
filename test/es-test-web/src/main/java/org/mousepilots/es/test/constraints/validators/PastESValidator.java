/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.PastES;
import org.mousepilots.es.test.constraints.annotations.PastESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class PastESValidator implements ConstraintValidator<PastESContainer, Request> {
    
    PastES[] past;
    
    @Override
    public void initialize(PastESContainer constraintAnnotation) {
        past = constraintAnnotation.value();
    }
    
    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for(PastES pe : past){
            PastESSingleValidator psv = new PastESSingleValidator();
            psv.initialize(pe);
            if(!psv.isValid(value, context)){
                return false;
            }
        }
        return true;
    }
}