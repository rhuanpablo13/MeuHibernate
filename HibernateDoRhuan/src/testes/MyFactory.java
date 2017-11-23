package testes;

import com.java.entitypersistence.EntityPersistence;
import com.java.factorymethod.ConnectionFactory;
import com.java.factorymethod.SelectDatabase;
import com.java.persistence.PersistenceUnit;

public class MyFactory {



	public static EntityPersistence getConnection() {

		ConnectionFactory factory = new ConnectionFactory(new PersistenceUnit("meujpa","root","root"));
		EntityPersistence entity = new EntityPersistence(factory.getConnection(SelectDatabase.MYSQL));
		return entity;

	}

}
