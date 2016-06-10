/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.mousepilots.es.test.constraints.annotations.PatternES;
import org.mousepilots.es.test.constraints.annotations.PatternESContainer;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 */
public class PatternESValidator implements ConstraintValidator<PatternESContainer, Request>{
    
    PatternES[] patterns;

    @Override
    public void initialize(PatternESContainer constraintAnnotation) {
        patterns = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext cvc) {
        for(PatternES pattern : patterns){
            PatternESSingleValidator psv = new PatternESSingleValidator();
            psv.initialize(pattern);
            if(!psv.isValid(value, cvc)){
                return false;
            }
        }
        return true;
    }
    
}
