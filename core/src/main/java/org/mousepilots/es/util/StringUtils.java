package org.mousepilots.es.util;

/**
 * Utilities class for Strings.
 * @author Nicky Ernste
 * @version 1.0, 16-11-2015
 */
public class StringUtils {

    /**
     * Check if a {@link String} is either {@code null} or is an empty string.
     * @param str The {@link String} to check.
     * @return {@code true} if {@code str} is {@code null} or empty, {@code false} otherwise.
     */
    public static boolean isNullOrEmpty(String str){
        return str == null || str.trim().equals("");
    }
    
    /**
     * Check if a {@link String} is either {@code null} or is only a white space.
     * @param str The {@link String} to check.
     * @return {@code true} if {@code str} is {@code null} or a white space, {@code false} otherwise.
     */
    public static boolean isNullOrWhiteSpace(String str){
        return str == null || str.equals(" ");
    }
}