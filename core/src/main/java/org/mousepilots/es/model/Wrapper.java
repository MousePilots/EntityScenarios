/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.io.Serializable;

/**
 *
 * @author ernsteni
 */
public interface Wrapper<T> extends Serializable {
    T unwrap();    
}
