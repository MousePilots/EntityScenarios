/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige.domain;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.model.AttributeES;
import org.mousepilots.es.core.model.ManagedTypeES;
import org.mousepilots.es.core.scenario.Context;
import org.mousepilots.es.server.scenario.privilige.domain.Privilige;
import org.mousepilots.es.server.scenario.privilige.domain.PriviligeService;
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author ap34wv
 */
public abstract class AbstractPriviligeService implements PriviligeService{

    /**
     * Gets the {@link Privilige}s applicable to the specified {@code scenario}, {@code operations} and {@code context}
     * @param scenario
     * @param operations
     * @param context
     * @return 
     */
    protected abstract Collection<Privilige> getPriviliges(String scenario, Set<CRUD> operations, Context context);

    @Override
    public Map<CRUD,Map<ManagedTypeES, Set<AttributeES>>> getPriviliges(String scenario, Context context){
        Map<CRUD,Map<ManagedTypeES,Set<AttributeES>>> retval = new EnumMap(CRUD.class);
        for(Privilige privilige : AbstractPriviligeService.this.getPriviliges(scenario, EnumSet.allOf(CRUD.class), context)){
            final Map<ManagedTypeES, Set<AttributeES>> typeToAttributes = Maps.getOrCreate(
                retval,
                privilige.getOperation(),
                TreeMap::new
            );
            Maps.getOrCreate(
                    typeToAttributes, 
                    privilige.getType(), 
                    TreeSet::new
            ).addAll(privilige.getAttributes());
        }
        return retval;
    }

    
    
}
