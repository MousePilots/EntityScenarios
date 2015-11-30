/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author clevenro
 */
public interface DtoManager {
    
    List<Dto> getAllDTOs();
    
    <I extends Serializable> Dto getDTO(int ordinal, I id);
}
