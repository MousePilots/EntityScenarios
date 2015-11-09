package org.mousepilots.es.model.impl;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the DescendingLongGenerator class.
 * @author Nicky Ernste
 * @version 4-11-2015
 */
public class DescendingLongGeneratorTest {
    
    private DescendingLongGenerator generator;
    
    @Before
    public void setUp() {
        generator = new DescendingLongGenerator();
    }
    
    @After
    public void tearDown() {
        generator = null;
    }

    /**
     * Test of generate method, of class DescendingLongGenerator.
     */
    @Test
    public void testGenerate() {
        Long expResult = Long.MAX_VALUE;
        Long result = generator.generate();
        assertEquals("Generated id was not Long.MAX_VALUE", expResult, result);
        for (int i = 0; i <= 1000; i++){
            expResult--;
            Assert.assertEquals("Generated id was not " + expResult, expResult, generator.generate());
        }
    }

    /**
     * Test of reset method, of class DescendingLongGenerator.
     */
    @Test
    public void testReset() {
        Long expResult = Long.MAX_VALUE;
        Long result = generator.generate();
        assertEquals("Generated id was not Long.MAX_VALUE", expResult, result);
        generator.generate();
        generator.generate();
        generator.generate();
        generator.generate();
        generator.generate();
        assertFalse("The generated id is still Long.MAX_VALUE!", generator.generate() == Long.MAX_VALUE);
        generator.reset();
        assertEquals("Generated id was not Long.MAX_VALUE", expResult, result);
    }    
}