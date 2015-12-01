package org.mousepilots.es.change.exception;

import org.mousepilots.es.change.Change;

/**
 * @author Roy Cleven
 */
public class IllegalChangeException extends RuntimeException{

    private Change change;
    private Reason reason;

    public IllegalChangeException() {
    }

    public IllegalChangeException(Change change, Reason reason) {
        this.change = change;
        this.reason = reason;
    }

    public Change getChange() {
        return change;
    }

    public Reason getReason() {
        return reason;
    }
    
}