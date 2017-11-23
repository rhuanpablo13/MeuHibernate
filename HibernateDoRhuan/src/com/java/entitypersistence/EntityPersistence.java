package com.java.entitypersistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.java.refflections.ReflectionsUtils;

public class EntityPersistence {

	private ReflectionsUtils utils;
	private Connection connection;


	public EntityPersistence(Connection connection) {
		// TODO Auto-generated constructor stub
		this.connection = connection;

	}


	public boolean createTable(Object object) {


		try {
			utils = new ReflectionsUtils(object);
			Statement stmt = this.connection.createStatement();
			String sql = utils.getSqlCreateTable();



			if (stmt.executeUpdate(sql) == 0) {
				System.out.println("Query de criar tabela executada com sucesso!");
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Tabela j� existente!");

		}
		return false;
	}


	public boolean persist(Object object) {

		if (object != null) {

			System.out.println("\npersistindo objeto...");

			try {
				String insert = utils.getSqlInsert();
				System.out.println(insert);
				Statement ps = this.connection.createStatement();
				ps.executeUpdate(insert);
				System.out.println("Query de inser��o executada com sucesso!");
				ps.close();
				return true;
			} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {

			}


		}
		return false;
	}


	public List<Object> selectTable(String table) {

		List<String> atr = utils.getResultSelect();
		List<Object> result = new ArrayList<Object>();

		try {
			String select = utils.getSqlSelect();
			Statement ps = this.connection.createStatement();
			ResultSet rs = ps.executeQuery(select);
			int count = 0;

			while (rs.next()) {

				for (String str : atr) {

					count++;
					switch (str) {
					case "int":
							result.add("\t"+rs.getInt(count));
							break;

					case "String":
							result.add("\t"+rs.getString(count));
							break;

					case "float":
							result.add("\t"+rs.getFloat(count));
							break;

					case "double":
							result.add("\t"+rs.getDouble(count));
							break;

					case "Date":
							result.add("\t"+rs.getDate(count));
							break;

					case "char":
							result.add("\t"+rs.getCharacterStream(count));
							break;
					}

				}
				result.add("\n");

			}
			System.out.println("Query de sele��o executada com sucesso!");
			ps.close();
			return result;

		} catch (SQLException | IllegalArgumentException e) {

		}
		return null;
	}



}
