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

    private Map<IdentifiableTypeES,Map<Serializable,Serializable>> clientIdtoPersistetId;
    private Map<CRUD,List<Serializable>> crudToChangedItems;
    
    public ExecutionSummaryImpl(){
        clientIdtoPersistetId = new HashMap<>();
        crudToChangedItems = new HashMap<>();
    }
    
    void addClientIdToPersistetId(IdentifiableTypeES type, Serializable clientId, Serializable persistedId){
        final Map<Serializable, Serializable> idToPersistedId = Maps.getOrCreate(clientIdtoPersistetId,type,HashMap::new);
        idToPersistedId.put(clientId, persistedId);
    }
    
    void addCrudToChangeItem(CRUD operation,Serializable item){
        final List<Serializable> itemList = Maps.getOrCreate(crudToChangedItems, operation, ArrayList::new);
        itemList.add(item);
    }
    
    @Override
    public Map<IdentifiableTypeES, Map<Serializable, Serializable>> getClientIdToPersistetId() {
        return clientIdtoPersistetId;
    }

    @Override
    public <T> List<T> get(Class<T> javaType, CRUD... operations) {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < operations.length; i++) {
            List<Serializable> crudList = crudToChangedItems.get(operations[i]);
            for (Serializable item : crudList) {
                if(item.getClass() == javaType)
                    returnList.add((T)item);
            }
        }
        return returnList;
    }
}