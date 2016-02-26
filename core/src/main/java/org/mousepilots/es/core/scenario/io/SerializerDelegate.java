package org.mousepilots.es.core.scenario.io;

/**
 *
 * @author jgeenen
 * @param <S>
 */
public abstract class SerializerDelegate<S extends Serializer> {

    private final S parent;

    protected SerializerDelegate(S parent) {
        this.parent = parent;
    }
    

    /**
     * @return the parent for which {@code this} is a delegate
     */
    protected S getParent() {
        return parent;
    }

    
}
