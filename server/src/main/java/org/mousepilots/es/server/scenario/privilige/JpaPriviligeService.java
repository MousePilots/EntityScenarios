/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.server.scenario.privilige;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.mousepilots.es.core.command.CRUD;
import org.mousepilots.es.core.scenario.Context;

/**
 *
 * @author ap34wv
 */
public abstract class JpaPriviligeService extends AbstractPriviligeService{
    
    /**
     * @return an entity manager which is open
     */
    protected abstract EntityManager getEntityManager();
    
    @Override
    protected List<Privilige> collect(String scenario, Set<CRUD> operations, Context context){
        final EntityManager entityManager = getEntityManager();
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Privilige> cq = cb.createQuery(Privilige.class);
        final Root<Privilige> privilige = cq.from(Privilige.class);
        cq.select(privilige);
        final Expression<Set<String>> userNames = privilige.get("userNames");
        List<Predicate> predicates = new LinkedList<>();
        predicates.add(
            cb.equal(
                privilige.get("scenario"),    
                scenario
            )
        );
        predicates.add(privilige.get("operation").in(operations));
        predicates.add( 
            cb.or( 
                cb.isEmpty(userNames), 
                cb.isMember(context.getUserName(), userNames) 
            ) 
        );
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        final List<Privilige> resultList = entityManager
                .createQuery(cq)
                .getResultList()
                .stream()
                .filter( p -> p.getRoleNames().isEmpty() || context.hasRoleIn(p.getRoleNames()) )
                .collect(Collectors.toCollection(ArrayList::new));
        
        return resultList;
    }
    
}
