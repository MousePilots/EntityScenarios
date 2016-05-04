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
 public enum AuthorizationStatus {
    UNAUTHORIZED, REQUIRES_PROCESSING, AUTHORIZED;

    public static AuthorizationStatus least(AuthorizationStatus... statuses) {
        int minOrdinal = Integer.MAX_VALUE;
        for (AuthorizationStatus status : statuses) {
            minOrdinal = Math.min(minOrdinal, status.ordinal());
        }
        return AuthorizationStatus.values()[minOrdinal];
    }

    public AuthorizationStatus least(AuthorizationStatus other) {
        if (this.ordinal() < other.ordinal()) {
            return other;
        } else {
            return this;
        }
    }
    
}
