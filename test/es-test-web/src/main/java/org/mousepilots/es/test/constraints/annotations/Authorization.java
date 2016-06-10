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
import org.mousepilots.es.test.constraints.validators.AuthorizationSingleValidator;

/**
 *
 * @author bhofsted
 */
@Repeatable(AuthorizationContainer.class)
@Constraint(validatedBy = AuthorizationSingleValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
String message() default "You do not have permission to alter objects of type " /*entity*/;

    Class<?> javaType();

    CRUD[] operation() default {CRUD.CREATE};
    
    String[] roles();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}