/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.NotNullES;
import org.mousepilots.es.test.constraints.annotations.NotNullESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class NotNullESValidator implements ConstraintValidator<NotNullESContainer, Request> {
    
    NotNullES[] notNull;
    
    @Override
    public void initialize(NotNullESContainer constraintAnnotation) {
        notNull = constraintAnnotation.value();
    }
    
    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        for(NotNullES nn : notNull){
            NotNullESSingleValidator nnsv = new NotNullESSingleValidator();
            nnsv.initialize(nn);
            if(!nnsv.isValid(value, context)){
                return false;
            }
        }
        return true;
    }
}
