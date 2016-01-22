package org.mousepilots.es.maven.model.generator.plugin.scratch;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
//@StaticMetamodel(User.class)
public abstract class User_ extends Person_ {

	public static volatile ListAttribute<User, Address> addresses;
	public static volatile ListAttribute<User, String> emailAddresses;
	public static volatile CollectionAttribute<User, Role> previousRoles;
	public static volatile CollectionAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SetAttribute<User, User> subordinates;
	public static volatile SingularAttribute<User, Account> account;
	public static volatile SingularAttribute<User, WorkEnvironment> workEnvironment;

}

