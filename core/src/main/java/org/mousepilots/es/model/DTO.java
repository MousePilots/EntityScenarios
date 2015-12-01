package org.mousepilots.es.model;

import java.io.Serializable;
import java.util.List;
import org.mousepilots.es.change.Change;

/**
 *
 * @author Roy Cleven
 * @version 1.0, 1-12-2015
 */
public interface DTO {

    IdentifiableTypeES getType();

    AttributeES getId();

    List<Change> getChanges();

    Class<?> getOriginalClass();

    /**
     * @param <S>
     * @return {@code this}'s {@link Id}-{@link AttributeES}'s value for an
     * identifiable type, otherwise {@code null};
     */
    <S extends Serializable> S getIdValue();

    /**
     * @param <S>
     * @return {@code this}'s {@link Version}-{@link AttributeES}'s value for a
     * versioned type, otherwise {@code null};
     */
    <S extends Serializable> S getVersionValue();
}