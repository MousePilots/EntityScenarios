/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints.annotations;

import org.mousepilots.es.test.constraints.validators.SingleEntityCreateValidator;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Payload;
import javax.validation.Constraint;

/**
 *
 * @author bhofsted
 */

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = SingleEntityCreateValidator.class)
public @interface SingleEntityCreate {
    String message() default "Can't create more than 1 "/*entity*/; //entity will be added by validator upon failure

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * The managed classes of which at most one instance may be created
     * @return 
     */
    Class<?>[] constrainedClasses();
//    @Target(TYPE)
//    @Retention(RUNTIME)
//    @interface List{
//        SingleEntityCreate[] value();
//    }
}
