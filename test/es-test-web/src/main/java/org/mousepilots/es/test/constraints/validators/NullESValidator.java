/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.NullES;
import org.mousepilots.es.test.constraints.annotations.NullESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class NullESValidator implements ConstraintValidator<NullESContainer, Request> {
    
    NullES[] isNull;
    
    @Override
    public void initialize(NullESContainer constraintAnnotation) {
        isNull = constraintAnnotation.value();
    }
    
    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for(NullES in : isNull){
            NullESSingleValidator nsv = new NullESSingleValidator();
            nsv.initialize(in);
            if(!nsv.isValid(value, context)){
                return false;
            }
        }
        return true;
    }
}