package jeff.model.domain;

import java.io.Serializable;

import jeff.util.Util;

public class Roupa implements Serializable {
    private int id;
    private String nome;
    private double valor;
    private int qtd;
    private int qtd_em_condicional;

    public Roupa() {
        this.id = 0;
        this.nome = "";
        this.valor = 0.00;
        this.qtd = 0;
        this.qtd_em_condicional = 0;
    }

    public Roupa(int id, String nome, double valor, int qtd, int qtd_em_condicional) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.qtd = qtd;
        this.qtd_em_condicional = qtd_em_condicional;
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

    public double getValorDouble() {
        return valor;
    }

    public String getValor() {
        return Util.toStringDinheiro(valor);
    }

    public void setValor(double valor) {
        this.valor = valor;
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
        return "Roupa [id=" + id + ", nome=" + nome + ", qtd=" + qtd
                + ", qtd_em_condicional=" + qtd_em_condicional + ", valor=" + valor + "]";
    }

    public String getKey() {
        return getId() + " : " + getNome();
    }

    public void setKey(String key) {
        String[] keyArray = key.split(" : ");
        setId(Integer.parseInt(keyArray[0]));
        setNome(keyArray[1]);
    }

    public int estoqueDisponinel() {
        return qtd - qtd_em_condicional;
    }
}
