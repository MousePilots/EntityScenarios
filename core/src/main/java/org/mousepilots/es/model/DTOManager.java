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
public interface DTOManager {
    
    List<DTO> getAllDTOs();
    
    <I extends Serializable> DTO getDTO(int ordinal, I id);
}
