/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.refflections;

/**
 *
 * @author Rhuan
 */
class Atributo {
    
    private String nomeClasse;
    private String nomeBanco;
    private String tipoClasse;
    private String tipoBanco;
    private boolean isNull;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;
    private int tamanho;
    private String valor;

    
    
    
    
    
    public void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public boolean isIsAutoIncrement() {
        return isAutoIncrement;
    }
    
    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    /**
     * @return the isNull
     */
    public boolean isIsNull() {
        return isNull;
    }

    /**
     * @param isNull the isNull to set
     */
    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    public boolean isIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * @return the tipoClasse
     */
    public String getTipoClasse() {
        return tipoClasse;
    }

    /**
     * @param tipoClasse the tipoClasse to set
     */
    public void setTipoClasse(String tipoClasse) {
        this.tipoClasse = tipoClasse;
    }

    /**
     * @return the tipoBanco
     */
    public String getTipoBanco() {
        return tipoBanco;
    }

    /**
     * @param tipoBanco the tipoBanco to set
     */
    public void setTipoBanco(String tipoBanco) {
        this.tipoBanco = tipoBanco;
    }
}
