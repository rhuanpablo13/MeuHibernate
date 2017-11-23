package testes;
import java.util.List;

import com.java.entitypersistence.EntityPersistence;

public class DAO <T> {

	private EntityPersistence entity;


	public DAO(EntityPersistence entity) {
		// TODO Auto-generated constructor stub
		this.entity = entity;

	}



	public void inserir (T t) {

		if (entity.persist(t)) {
			System.out.println("Persistencia executada com sucesso");
		} else {
			System.out.println("Objeto j� persistido");
		}

	}


	public void createTable (T t) {

		if (entity.createTable(t)) {
			System.out.println("Tabela criada com sucesso");
		}
	}


	public void select (T t) {

		List<Object> retorno = entity.selectTable(t.getClass().getSimpleName());
		if(retorno != null) {

			for (Object object : retorno) {
				System.out.print(object.toString());
			}
		}
	}

}
