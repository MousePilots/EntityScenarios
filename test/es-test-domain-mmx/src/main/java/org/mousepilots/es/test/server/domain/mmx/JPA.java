package org.mousepilots.es.test.server.domain.mmx;




import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.mousepilots.es.core.model.EntityManagerES;
import org.mousepilots.es.core.model.MetamodelES;
import org.mousepilots.es.core.model.impl.EntityManagerFactoryImpl;
import org.mousepilots.es.test.domain.MetamodelImpl;


public class JPA {

    static {
        MetamodelImpl.init();
    }
    
    private static final String PERSISTENCE_UNIT_NAME = "mmx_test";
    
    protected static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    
    protected static final org.mousepilots.es.core.model.EntityManagerFactory ENTITY_MANAGER_FACTORY_ES = new EntityManagerFactoryImpl();
    
    public static EntityManager createEntityManager(){
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
    
    public static EntityManagerES createEntityManagerES(){
        return ENTITY_MANAGER_FACTORY_ES.createEntityManager();
    }
    
    public static MetamodelES getMetaModel(){
        return MetamodelImpl.INSTANCE;
    }
    
    protected Logger getLogger(){
        return Logger.getLogger(getClass().getName());
    }

    protected JPA(){}

}
