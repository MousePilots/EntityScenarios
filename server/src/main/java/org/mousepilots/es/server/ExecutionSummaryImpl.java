package org.mousepilots.es.server;

import org.mousepilots.es.core.change.ExecutionSummary;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mousepilots.es.core.change.CRUD;
import org.mousepilots.es.core.model.IdentifiableTypeES;
import org.mousepilots.es.core.util.Maps;

public class ExecutionSummaryImpl implements ExecutionSummary {

    /**
     * Contains the combination of the client side id and the persistet id 
     * based on the {@link IdentifiableTypeES}.
     */
    private final Map<IdentifiableTypeES, Map<Serializable, Serializable>> clientIdToPersistetId;
    
    /**
     * Holds the combinations of which operation was performed on which items.
     */
    private final Map<CRUD, List<Serializable>> crudToChangedItems;

    public ExecutionSummaryImpl() {
        clientIdToPersistetId = new HashMap<>();
        crudToChangedItems = new HashMap<>();
    }

    /**
     * adds an entry to the clientIdToPersistetId.
     *
     * @param type type of the identifiable
     * @param clientId id given to the identifiable on client side
     * @param persistedId id given to the identifiable on server side
     */
    void addClientIdToPersistetId(IdentifiableTypeES type, Serializable clientId, Serializable persistedId) {
        final Map<Serializable, Serializable> idToPersistedId = Maps.getOrCreate(clientIdToPersistetId, type, HashMap::new);
        idToPersistedId.put(clientId, persistedId);
    }

    /**
     * Stores an entry in the crudToChangedItems
     *
     * @param operation which operation was performed.
     * @param item which identifable it was performed on.
     */
    void addCrudToChangeItem(CRUD operation, Serializable item) {
        final List<Serializable> itemList = Maps.getOrCreate(crudToChangedItems, operation, ArrayList::new);
        itemList.add(item);
    }

    /**
     * 
     * @return A map containing the link between client-side id's and persisted id's
     */
    @Override
    public Map<IdentifiableTypeES, Map<Serializable, Serializable>> getClientIdToPersistetId() {
        return clientIdToPersistetId;
    }

    /**
     * Looks up the given class and operation and provides a list with all the instances which were changed in the transaction.
     * @param <T> Class type
     * @param javaType Class which needs to be looked up
     * @param operations Array of operations which has to be looked up.
     * @return list with all the changed instances of the classtype.
     */
    @Override
    public <T> List<T> get(Class<T> javaType, CRUD... operations) {
        List<T> returnList = new ArrayList<>();
        for (CRUD operation : operations) {
            List<Serializable> crudList = crudToChangedItems.get(operation);
            for (Serializable item : crudList) {
                if (item.getClass() == javaType) {
                    returnList.add((T) item);
                }
            }
        }
        return returnList;
    }
}
