Estudo sobre o funcionamento da API JPA, implementando recursos básicos da especificação.
Os recursos implementados foram:

- Coluna (@Column)
- PrimaryKey (@PrimaryKey)
- Tabela (@Table)

Utilizando os recursos de Reflection, Annotation e JDBC juntos, implementando
a persistencia e consulta automatizada dos dados.

Como o exemplo abaixo:


Classe modelo:

```java
@Tabela
public class Pessoa {
    @PrimaryKey()
    private int id;

    @Coluna(tamanho = 45)
    private String nome;

    @Coluna
    private int idade;

    @Coluna
    private double salario;
}
```

Fábrica de conexões:
```java
public class MyFactory {

    public static EntityPersistence getConnection() {
        ConnectionFactory factory = new ConnectionFactory(new PersistenceUnit("meujpa","root","root"));
        EntityPersistence entity = new EntityPersistence(factory.getConnection(SelectDatabase.MYSQL));
        return entity;
    }
}
```

Classe DAO:
```java
public class DAO <T> {

    private EntityPersistence entity;

    public DAO(EntityPersistence entity) {
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
```


Classe Principal:
```java
public class Principal {

    public static void main(String[] args) {

        Pessoa pessoa = new Pessoa();
        pessoa.setId(100);
        pessoa.setIdade(15);
        pessoa.setNome("João da Silva");
        pessoa.setSalario(1500.00);

        // instanciando a camada de persistencia
        PersistenceUnit unit = new PersistenceUnit("meujpa", "root", "root");
        MyFactory factory = new MyFactory();
        EntityPersistence entity = factory.getConnection();
        DAO<Pessoa> dao = new DAO<>(entity);

        // criando a tabela inserindo os dados e consultando
        dao.createTable(pessoa);
        dao.inserir(pessoa);
        dao.select(pessoa);
    }
}
```
