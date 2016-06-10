/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.annotations;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.mousepilots.es.test.constraints.validators.SizeESValidator;

/**
 *
 * @author bhofsted
 */
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = SizeESValidator.class)
public @interface SizeESContainer {
    String message() default " is too low.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    SizeES[] value();
}