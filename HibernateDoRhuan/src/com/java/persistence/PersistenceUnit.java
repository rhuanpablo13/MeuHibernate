package com.java.persistence;

public class PersistenceUnit {

	private String banco;
	private String usuario;
	private String senha;


	public PersistenceUnit(String banco, String usuario, String senha) {
		// TODO Auto-generated constructor stub
		this.banco = banco;
		this.usuario = usuario;
		this.senha = senha;
	}


	public String getBanco() {
		return banco;
	}

	public String getSenha() {
		return senha;
	}

	public String getUsuario() {
		return usuario;
	}


}
