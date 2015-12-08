package org.mousepilots.es.maven.model.generator.plugin.scratch;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import org.mousepilots.es.core.model.MappedSuperclassTypeES;
import org.mousepilots.es.core.model.impl.MappedSuperclassTypeESImpl;

/**
 *
 * @author ernsteni
 */
@Generated(value="EntityScenarios0.1", date="2015-11-13T13:08:55")
//@StaticMetamodel(Account.class)
public class Account_ES {

    public static final SingularAttribute<Account, ? extends Person> person = null;
    public static final SingularAttribute<Account, String> name = null;
    public static final SingularAttribute<Account, Long> id = null;
    private static final MappedSuperclassTypeES<Account> INSTANCE_ES = null;

    public static MappedSuperclassTypeES<Account> getInstance() {
        return INSTANCE_ES;
    }
}