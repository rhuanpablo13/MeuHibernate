package com.java.factorymethod;

import java.sql.Connection;

import com.java.persistence.PersistenceUnit;

public class ConnectionFactory {

	private static PersistenceUnit unit;

	public ConnectionFactory(PersistenceUnit unit) {
		this.unit = unit;
	}

	public static Connection getConnection(SelectDatabase database) {

		switch (database) {
		case ORACLE:
			return new ConnectionOracle(unit.getBanco(), unit.getUsuario(), unit.getSenha()).getConnection();

		case MYSQL:
			return new ConnectionMysql(unit.getBanco(), unit.getUsuario(), unit.getSenha()).getConnection();
		}
		return null;
	}
}
