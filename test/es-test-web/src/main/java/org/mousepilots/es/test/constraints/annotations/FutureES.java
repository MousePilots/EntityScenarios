/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.test.constraints.validators.FutureESSingleValidator;

/**
 * 
 * @author bhofsted
 */
@Repeatable(FutureESContainer.class)
@Constraint(validatedBy = FutureESSingleValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureES {
    
    String message() default " has to be in the future.";

    Class<?> javaType();

    String attribute();

    CRUD operation();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}