/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model;

/**
 *
 * @author geenenju
 */
public interface HasAttribute<M, A,AD extends AttributeES<M,A>> {
     
     AD getAttribute();
}
