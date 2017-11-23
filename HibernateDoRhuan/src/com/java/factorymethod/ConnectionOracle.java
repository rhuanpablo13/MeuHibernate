package com.java.factorymethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionOracle extends ConnectionAbstract {


	public ConnectionOracle(String banco, String usuario, String senha) {
		// TODO Auto-generated constructor stub
		this.banco = banco;
		this.usuario = usuario;
		this.senha = senha;
	}


	public Connection getConnection() {

		if(connection == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String con = "jdbc:oracle:thin:@localhost:1521:" + banco;
				this.connection = DriverManager.getConnection(con, usuario, senha);
				System.out.println("Coex�o com oracle criada com sucesso em: " + banco);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao abrir conex�o com Oracle!");
			}
		}

		return connection;

	}
}
