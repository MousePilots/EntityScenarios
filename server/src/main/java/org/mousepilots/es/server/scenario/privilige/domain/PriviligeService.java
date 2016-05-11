/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige.domain;

import java.util.Map;
import java.util.Set;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.Context;

/**
 *
 * @author ap34wv
 */
public interface PriviligeService {

    /**
     * Creates mappings from {@link CRUD} {@code operation} &rarr; {@link ManagedTypeES} type &rarr; {@link Set}{@code <}{@link AttributeES}{@code >} attributes. The interpretation
     * of each such mapping is that the caller principal has privilige(s) which allow the {@code operation} is on the {@code attributes} of the {@code type}.
     * Since {@link CRUD#DELETE}s priviliges are defined at the type-level, the corresponding {@code attributes} are meaningless and hence always empty.
     * @param scenario the current scenario
     * @param context the current context
     * @return mappings from {@link CRUD} {@code operation} &rarr; {@link ManagedTypeES} type &rarr; {@link Set}{@code <}{@link AttributeES}{@code >} attributes
     */
    Map<CRUD, Map<ManagedTypeES, Set<AttributeES>>> getPriviliges(String scenario, Context context);
    
}
