package testes;

import java.sql.SQLException;
import java.sql.Statement;

import com.java.entitypersistence.EntityPersistence;
import com.java.factorymethod.ConnectionFactory;
import com.java.factorymethod.SelectDatabase;
import com.java.persistence.PersistenceUnit;

public class Principal {



	public static void main(String[] args) {

		Pessoa pessoa = new Pessoa();
		pessoa.setId(100);
		pessoa.setIdade(15);
		pessoa.setNome("Jo�o da Silva");
		pessoa.setSalario(1500.00);


		PersistenceUnit unit = new PersistenceUnit("meujpa", "root", "root");
		MyFactory factory = new MyFactory();
		EntityPersistence entity = factory.getConnection();
		DAO<Pessoa> dao = new DAO<>(entity);

		dao.createTable(pessoa);
		dao.inserir(pessoa);
		dao.select(pessoa);




	}
}
