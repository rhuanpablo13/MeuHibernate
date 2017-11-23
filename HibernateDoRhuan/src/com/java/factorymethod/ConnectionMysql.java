package com.java.factorymethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionMysql extends ConnectionAbstract {


	public ConnectionMysql(String banco, String usuario, String senha) {

		this.banco = banco;
		this.usuario = usuario;
		this.senha = senha;
	}


	public Connection getConnection() {

		if(connection == null) {
			System.out.println("Conex�o null...");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String con = "jdbc:mysql://localhost/" + banco;
				this.connection = DriverManager.getConnection(con, usuario, senha);
				System.out.println("Coex�o com mysql criada com sucesso em: " + banco);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao abrir conex�o com Mysql!");
			}
		}

		return connection;

	}

}
