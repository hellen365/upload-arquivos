package com.vhns.uploadarquivos.processarArquivo;

public class FrequenciaPalavras {

    private String palavra;
    private Integer frequencia;

    public FrequenciaPalavras(){

    }

    public FrequenciaPalavras(String palavra, Integer frequencia) {
        this.palavra = palavra;
        this.frequencia = frequencia;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "FrequenciaPalavras{" +
                "palavra='" + palavra + '\'' +
                ", frequencia=" + frequencia +
                '}';
    }
}
