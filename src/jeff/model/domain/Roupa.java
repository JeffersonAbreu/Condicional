package jeff.model.domain;

import java.io.Serializable;

public class Roupa implements Serializable{
    private int id;
    private String nome;
    private double valor;
    private int cod_barras;
    private int qtd;
    private int qtd_em_condicional;

    public Roupa() {
    }

    public Roupa(int id, String nome, double valor, int cod_barras, int qtd, int qtd_em_condicional) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.cod_barras = cod_barras;
        this.qtd = qtd;
        this.qtd_em_condicional = qtd_em_condicional;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(int cod_barras) {
        this.cod_barras = cod_barras;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getQtd_em_condicional() {
        return qtd_em_condicional;
    }

    public void setQtd_em_condicional(int qtd_em_condicional) {
        this.qtd_em_condicional = qtd_em_condicional;
    }

    @Override
    public String toString() {
        return "Roupa [cod_barras=" + cod_barras + ", id=" + id + ", nome=" + nome + ", qtd=" + qtd
                + ", qtd_em_condicional=" + qtd_em_condicional + ", valor=" + valor + "]";
    }

}
