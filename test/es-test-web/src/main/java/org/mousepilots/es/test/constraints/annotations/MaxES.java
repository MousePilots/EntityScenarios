/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.annotations;

import org.mousepilots.es.test.constraints.validators.MinESSingleValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.mousepilots.es.core.command.CRUD;

/**
 *
 * @author bhofsted
 */
@Repeatable(MaxESContainer.class)
@Constraint(validatedBy = MinESSingleValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxES {

    String message() default " is too high.";

    Class<?> javaType();

    String attribute();

    CRUD operation();

    int max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
