package org.mousepilots.es.core.util;

import java.util.Collection;
import javax.persistence.Id;
import org.mousepilots.es.core.model.MemberES;
import org.mousepilots.es.core.model.impl.IdentifiableTypeESImpl;

/**
 * Utilities class for identifiable types.
 * @author Jurjen van Geenen
 * @version 1.0, 25-11-2015
 */
public class IdentifiableUtils {

    /**
     * Adds the {@link Id}-values of the {@code identifiables} to the supplied
     * {@code ids} collection.
     *
     * @param <E> the type of the identifiable type.
     * @param <I> the type of the id.
     * @param type the identifiable type to add the id for.
     * @param identifiables a collection of the type for the identifable type.
     * @param ids a collection to add the ids to.
     * @return the supplied {@link ids} collection for chaining
     */
    public static <E, I> Collection<I> addIds(IdentifiableTypeESImpl<E> type, Collection<E> identifiables, Collection<I> ids) {
        final MemberES idMember = (MemberES) type.getId().getJavaMember();
        for (Object identifiable : identifiables) {
            ids.add(idMember.get(identifiable));
        }
        return ids;
    }
}