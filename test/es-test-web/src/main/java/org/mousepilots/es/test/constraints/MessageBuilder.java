/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints;

import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author bhofsted
 */
public class MessageBuilder {

    public void buildMessage(ConstraintValidatorContext cvc, String preText, String postText) {
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(
                preText + cvc.getDefaultConstraintMessageTemplate() + postText)
                .addConstraintViolation();
    }
}
