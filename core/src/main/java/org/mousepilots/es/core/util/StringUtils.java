package org.mousepilots.es.core.util;

import java.util.Iterator;
import java.util.List;

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
    
    /**
     * 
     * @param clazz
     * @param fieldValuePairs a serialized list of the form {@code field0,value0,field1,value1,...}
     * @return 
     */
    public static String createToString(Class clazz, List fieldValuePairs){
        StringBuilder retval = new StringBuilder(clazz.getName()).append('[');
        for(Iterator i = fieldValuePairs.iterator(); i.hasNext(); ){
            retval.append(i.next()).append("=").append(i.next());
            if(i.hasNext()){
                retval.append(", ");
            }
        }
        return retval.append(']').toString();
    }
}