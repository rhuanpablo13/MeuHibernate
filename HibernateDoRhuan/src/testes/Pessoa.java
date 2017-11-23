package testes;

import java.util.Date;

import com.java.annotations.Coluna;
import com.java.annotations.PrimaryKey;
import com.java.annotations.Tabela;

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


	public Pessoa() {
		// TODO Auto-generated constructor stub
	}





	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", idade=" + idade + ", salario=" + salario + "]";
	}





	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public int getIdade() {
		return idade;
	}



	public void setIdade(int idade) {
		this.idade = idade;
	}



	public double getSalario() {
		return salario;
	}



	public void setSalario(double salario) {
		this.salario = salario;
	}



}
