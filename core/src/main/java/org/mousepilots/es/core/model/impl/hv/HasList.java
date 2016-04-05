/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.core.model.impl.hv;

import java.util.List;
import org.mousepilots.es.core.model.HasValue;

/**
 *
 * @author jgeenen
 */
public final class HasList<T> extends AbstractHasCollection<List> implements HasValue<List>{

    @Override
    protected List createEmpty() {
        return createEmptyList();
    }
}
