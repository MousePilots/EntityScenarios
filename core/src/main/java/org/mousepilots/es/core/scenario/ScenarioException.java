/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.scenario;

/**
 *
 * @author ap34wv
 */
public class ScenarioException extends RuntimeException{
    
    public static enum Reason{
        OPERATION_NOT_ALLOWED_ON_TYPE,
        OPERATION_NOT_ALLOWED_ON_ATTRIBUTE,
        TYPE_NOT_IN_GRAPH;
    }

    private Reason reason;

    protected ScenarioException(){}
    
    public ScenarioException(Reason reason) {
        this.reason = reason;
    }

    public final Reason getReason() {
        return reason;
    }
}
