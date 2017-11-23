package com.java.refflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.annotations.Coluna;
import com.java.annotations.PrimaryKey;
import com.java.annotations.Tabela;
import com.java.factorymethod.ConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReflectionsUtils {


        /**
         * Classe que está sendo recebida para o mapeamento
         */
	private static Object classe = null;


	/**
	 * Classe que vai representar uma tabela
	 */
	private final TabelaMapeada tabela;
        
        
        /**
         * Atributos da classe
         */
        private List<Atributo> atributos;

        
        
        public ReflectionsUtils(Object classe) {
            
            if(isNullObject()) 
               this.classe = classe;
            
            this.tabela = new TabelaMapeada();
        }

        
        private boolean isNullObject() {            
            if(classe != null)
                return true;            
            return  false;
        }

        
	/**
	 * Faz o mapeamento do nome da classe correspondente ao da tabela a ser criada
	 * @return String
	 */
	protected String mapNameClass() {

            if(tabela.getNome() == null) {
                
                if(classe.getClass().isAnnotationPresent(Tabela.class)) {

			Object value;
			try {
				Annotation tabelaAnnotation = classe.getClass().getAnnotation(Tabela.class);
				Class<? extends Annotation> type = tabelaAnnotation.annotationType();
				Method[] metodos = type.getMethods();
				value = metodos[0].invoke(tabelaAnnotation, (Object[])null);

				if (! value.toString().equalsIgnoreCase("null")) {
                                    return value.toString();
				} else {
				    return (classe.getClass().getSimpleName());
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                System.out.println("Erro ao recuperar nome da Classe!");
                        }

		}
            }
            return tabela.getNome();
	}


	/**
	 * Pequeno conversor de tipos de atributos de classe para banco
	 * @param String tipo
	 * @return String
	 */
	private String parseTipoAtributo(String tipo) {

		switch (tipo) {
		case "String":
                    return "varchar";
                case "Calendar":
                    return "Date";
		default:
                    return tipo;
		}
	}

        
        /**
         * Faz o mapeamento de todos os atributos do tipo Primary Key
         * @return boolean
         */
        protected boolean mapPrimaryKeys() {
            
            try {
                    
                    for (Field field : classe.getClass().getDeclaredFields()) {

                        Atributo atributo;
                        if (field.isAnnotationPresent(PrimaryKey.class)) {
                            
                            //manda para o método responsável por fazer o mapeamento
                            //de um campo do tipo primary key
                            atributo = mappedFieldPrimaryKey(field);
                            if (atributo != null) {
                                atributos.add(atributo);
                            }
                        }
                    }
                    return true;
                    
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao mapear os atributos primary key");
            }
            return false;
        }
        
        
        /**
         * Faz o mapeamento de um atributo do tipo Primary Key:
         * Nome do atributo
         * Tipo do atributo
         * @param field 
         */
	private Atributo mappedFieldPrimaryKey(Field field) {

            
            Atributo atributo = new Atributo();
            atributo.setIsPrimaryKey(true);
            
            //recupera o tipo do atributo
            atributo.setTipoBanco(parseTipoAtributo(field.getType().getSimpleName()));
            atributo.setTipoClasse(field.getType().getSimpleName());
            
            //Se o nome da annotation estiver preenchida, o campo nome 
            //do banco recebe este valor, se não recebe o da própria classe
            String pk = field.getAnnotation(PrimaryKey.class).nome();            
            if(! pk.equalsIgnoreCase("null")) {
                atributo.setNomeBanco(pk);
                atributo.setNomeClasse(field.getName());
            } else {
                atributo.setNomeBanco(field.getName());
                atributo.setNomeClasse(field.getName());
            }
            
            //mapeia o auto_increment
            atributo.setIsAutoIncrement(field.getAnnotation(PrimaryKey.class).auto_increment());
            
            //mapeia o valor
            field.setAccessible(true);
            try {
                Object obj = field.get(classe);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ReflectionsUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ReflectionsUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return atributo;
	}
        
        
        private String parseValueString(String field) {
            
            return null;
        }
        
        
        /**
	 * Cria a slq de insert do objeto
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String getSqlInsert() throws IllegalArgumentException, IllegalAccessException {

		String sql = "insert into " + getNameClass() + "(";


		//seleciona os atributos da classe no array salvo
		String lastIndex = atributosClasse.get(atributosClasse.size()-1);
		for (String atr : atributosClasse) {
			sql +=  atr;
			if (atr != lastIndex) {
				sql += ", ";
			}
		}


		sql += ") values (";


		//Recupera os valores setados pelo usu�rio no objeto
        Field[] fields = classe.getClass().getDeclaredFields();


        for (Field f : fields) {

        	if (f.isAnnotationPresent(Coluna.class) || f.isAnnotationPresent(PrimaryKey.class)){
        		f.setAccessible(true);
        		Object value = f.get(classe);

        		//se o atributo da classe for do tipo string, na query ele precisa estar ente aspas simples
        		if(f.getType().toString().equalsIgnoreCase("class java.lang.String")) {
        			value = "'" + value + "'";
        		}
        		if(value != null) {
        			valoresObjeto.add(value.toString());
        		}

        	}
		}


        lastIndex = valoresObjeto.get(valoresObjeto.size()-1);
		for (String value : valoresObjeto) {
			sql +=  value;
			if (value != lastIndex) {
				sql += ", ";
			}
		}
        sql += ");";

		return sql;
	}


        

        
        
	/**
	 * Monta o trecho do sql pertinente aos atributos de primary key da tabela
	 * @return String
	 */
	protected String getSqlPrimaryKey() {

            String sql = "";
            //Ex 1:  id int
            //Ex 2:  id int not null
            //Ex 3:  id int not null auto_increment

            for (Atributo atributo : atributos) {
                
                if(atributo.isIsPrimaryKey()) {
                    sql += atributo.getNomeBanco();
                    sql += atributo.getTipoBanco();
                    sql += " not null ";
                    if(atributo.isIsAutoIncrement())
                        sql += " auto_increment, ";
                    else
                        sql += ", ";
                }
            }
            return sql;
	}


	/**
	 * Cria a parte do sql pertinente aos demais atributos que n�o s�o do tipo Primary Key da tabela
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private String getParametersCreateTable() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {


		String query = "";

		try {

            for (Field field : classe.getClass().getDeclaredFields()) {
                   if (field.isAnnotationPresent(Coluna.class)){

                	   //nome do atributo
                	   String name = getNameAttribute(field);
                	   if(name.equalsIgnoreCase("")) {
                		   query += field.getName();
                	   }else{
                		   query += name;
                	   }

                	   //tipo do atributo
                	   query += " " +  parseTipoAtributo(field.getType().getSimpleName());


                	   //tamanho do atributo
                	   int tamanho = getSizeAttribute(field);
                	   if(tamanho > 0) {
                		   query += " (" + tamanho + ") ";
                	   }


                	   //null ou not null do atributo
                	   if(!getNullAttribute(field)) {
                		   query += " not null, ";
                	   }

                   }
                   query += "\n";
            }
	     } catch (IllegalArgumentException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	     }


		return query;
	}


	public List<String> getResultSelect() {
		return tiposAtributosClasse;
	}



        protected boolean mappAtributes() {
            
            try {
                    
                    for (Field field : classe.getClass().getDeclaredFields()) {

                        Atributo atributo;
                        if (field.isAnnotationPresent(Coluna.class)) {
                            
                            //manda para o método responsável por fazer o mapeamento
                            //de um campo do tipo coluna
                            atributo = mappedAttribute(field);
                            if (atributo != null) {
                                atributos.add(atributo);
                            }
                        }
                    }
                    return true;
                    
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao mapear os atributos colunas");
            }
            return false;
        }
        
	/**
	 * Retorna o nome do atributo na classe, ou o setado na annotation @Coluna
	 * @param field
	 * @return String
	 */
	protected Atributo mappedAttribute(Field field) {

            Atributo atributo = new Atributo();
            
            //mapeamento do nome
            String stAtributo = field.getAnnotation(Coluna.class).nome();
	    
            if(stAtributo.equalsIgnoreCase("null")) {
                atributo.setNomeBanco(field.getName());
                atributo.setNomeClasse(field.getName());
            } else {
                atributo.setNomeBanco(stAtributo);
                atributo.setNomeClasse(field.getName());
            }
            
            //mapeamento do tipo
            atributo.setTipoBanco(parseTipoAtributo(field.getName()));
            atributo.setTipoClasse(field.getName());
            
            
            //mapeamento is null
            atributo.setIsNull(field.getAnnotation(Coluna.class).nulo());
            
            //mapeamento do tamanho
            atributo.setTamanho(field.getAnnotation(Coluna.class).tamanho());
            
            return atributo;
	}



	







	/**
	 * Cria a sql para criar a tabela
	 * @return
	 */
	public String getSqlCreateTable() {

		String sql = "";
		try {

			sql += "create table ";
			sql += getNameClass();
			sql += "( \n";


			//Parte do sql referente as chaves primarias no come�o da query
			String pk = getSqlPrimaryKey();
			if(pk == "")
				return "Annotation @PrimaryKey precisa ser informada";
			sql += pk;


			sql += getParametersCreateTable();


			// primary keys(valores[]);
			sql += "primary key(";
			String lastIndex = primaryKey.get(primaryKey.size()-1);
			for (String pks : primaryKey) {
				sql +=  pks;
				if (pks != lastIndex) {
					sql += ", ";
				}
			}
			sql += "));";


		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return sql;
	}




	

	public String getSqlSelect() {
		return "select * from " + getNameClass() + ";";
	}






	private boolean getNullAttribute(Field field) {

		return field.getAnnotation(Coluna.class).nulo();
	}


	private int getSizeAttribute(Field field) {

		return field.getAnnotation(Coluna.class).tamanho();
	}

	private boolean getAutoIncrementPrimaryKey(Field field) {

		return field.getAnnotation(PrimaryKey.class).auto_increment();
	}


}
