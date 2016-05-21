/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario.priviliges;

import org.mousepilots.es.core.scenario.Context;

/**
 *
 * @author ap34wv
 */
public interface PriviligeService {

    /**
     * Gets the caller principal's {@link Priviliges} in the specified {@code scenario} and {@code context}
     * @param scenario
     * @param context
     * @return the caller principal's {@link Priviliges} in the specified {@code scenario} and {@code context}
     */
    Priviliges getPriviliges(String scenario, Context context);
    
}
