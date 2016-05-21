/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import org.mousepilots.es.core.scenario.priviliges.Priviliges;
import org.mousepilots.es.core.scenario.priviliges.PriviligeService;
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
import org.mousepilots.es.core.util.Maps;

/**
 *
 * @author ap34wv
 */
public abstract class AbstractPriviligeService implements PriviligeService{

    /**
     * Collects the {@link Privilige}s applicable to the specified {@code scenario}, {@code operations} and {@code context}.
     * @param scenario
     * @param operations
     * @param context
     * @return 
     */
    protected abstract Collection<Privilige> collect(String scenario, Set<CRUD> operations, Context context);

    
    @Override
    public Priviliges getPriviliges(String scenario, Context context){
        final Map<CRUD,Map<ManagedTypeES,Set<AttributeES>>> operation2type2attributes = new EnumMap(CRUD.class);
        final Collection<Privilige> priviligeCollection = collect(scenario, EnumSet.allOf(CRUD.class), context);
        for(Privilige privilige : priviligeCollection){
            final Map<ManagedTypeES, Set<AttributeES>> typeToAttributes = Maps.getOrCreate(
                operation2type2attributes,
                privilige.getOperation(),
                TreeMap::new
            );
            final Set<AttributeES> attributes = Maps.getOrCreate(
                typeToAttributes,
                privilige.getType(), 
                TreeSet::new
            );
            attributes.addAll(privilige.getAttributes());
        }
        return new Priviliges(operation2type2attributes);
    }

    
    
}
