package gov.utah.epolst.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PolstUser.class)
public class PolstUser_ {

	public static volatile SingularAttribute<PolstUser, String> lastName;
    public static volatile SingularAttribute<PolstUser, String> firstName;

}
