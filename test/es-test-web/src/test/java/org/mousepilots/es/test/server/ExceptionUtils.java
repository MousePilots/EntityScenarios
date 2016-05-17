/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.server;

import org.junit.Assert;



/**
 *
 * @author AP34WV
 */
public class ExceptionUtils {
    
    public static <T extends Throwable> void assertThrows(Runnable runnable, Class<T> throwableClass){
        Throwable throwable = null;
        try{
            runnable.run();
        } catch(Throwable caught){
            throwable = caught;
        } finally {
            Assert.assertNotNull("no exception was thrown", throwable);
            Assert.assertTrue("expected throwable of " + throwableClass + ", observed " + throwable.getClass(), throwableClass.isAssignableFrom(throwable.getClass()));
        }
    }
    
}
