/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.refflections;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Rhuan
 */
public class TabelaMapeada {

    private String nome;    
    private List<Atributo> atributos;

    public TabelaMapeada() {
        this.nome = null;
        this.atributos = new ArrayList<>();
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }
    
    
    
}
