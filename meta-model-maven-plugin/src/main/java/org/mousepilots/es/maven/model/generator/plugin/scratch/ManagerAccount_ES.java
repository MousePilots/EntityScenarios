package org.mousepilots.es.maven.model.generator.plugin.scratch;

import javax.annotation.Generated;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.Type;
import org.mousepilots.es.model.EntityTypeES;
import org.mousepilots.es.model.SingularAttributeES;
import org.mousepilots.es.model.impl.EntityTypeESImpl;

/**
 *
 * @author ernsteni
 */
@Generated(value="EntityScenarios0.1", date="2015-11-13T13:08:55")
@StaticMetamodel(ManagerAccount.class)
public class ManagerAccount_ES extends Account_ES {

    public static final SingularAttributeES<ManagerAccount, Manager> person = null;
    public static final SingularAttributeES<ManagerAccount, Long> id = null;
    private static final EntityTypeES<ManagerAccount> INSTANCE_ES = new EntityTypeESImpl<>(Bindable.BindableType.ENTITY_TYPE, null, id, person, person, person, null, null, true, true, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, null, Type.PersistenceType.ENTITY, null, true, null, null, null);

}
