package org.mousepilots.es.maven.model.generator.plugin.scratch;

import java.util.Arrays;
import java.util.Set;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import org.mousepilots.es.core.model.impl.AbstractMetaModelES;

/**
 *
 * @author Nicky Ernste
 * @version 1.0, 8-jan-2016
 */
public class MetaModelImpl extends AbstractMetaModelES {



    private static void doInit() {
        MetaModelImpl mmi = new MetaModelImpl();
        mmi.setInstance(mmi);
        mmi.register(User_ES.getInstance());
        mmi.register(Account_ES.getInstance());
        //et cetera
    }

    public static void init(){
        if(AbstractMetaModelES.getInstance()==null){
            doInit();
        }
    }


    private MetaModelImpl(){
    }

}