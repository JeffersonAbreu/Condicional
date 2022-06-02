package jeff.model.domain;

import java.io.Serializable;

public class ItensCondicional implements Serializable {
    private Condicional condicional;
    private Roupa roupa;
    private double valorUni;
    private int qtd;
    private double valorTotal;

    public ItensCondicional() {
    }

    public ItensCondicional(Condicional condicional, Roupa roupa, double valor_uni, int qtd, double valor_total) {
        this.condicional = condicional;
        this.roupa = roupa;
        this.valorUni = valor_uni;
        this.qtd = qtd;
        this.valorTotal = valor_total;
    }

    public Roupa getRoupa() {
        return roupa;
    }

    public void setRoupa(Roupa roupa) {
        this.roupa = roupa;
    }

    public Condicional getCondicional() {
        return condicional;
    }

    public void setCondicional(Condicional condicional) {
        this.condicional = condicional;
    }

    public double getValorUni() {
        return valorUni;
    }

    public void setValorUni(double valor_uni) {
        this.valorUni = valor_uni;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valor_total) {
        this.valorTotal = valor_total;
    }

    @Override
    public String toString() {
        return "ItensCondicional [condicional=" + condicional + ", qtd=" + qtd + ", roupa=" + roupa + ", valor_total="
                + valorTotal + ", valor_uni=" + valorUni + "]";
    }

}
