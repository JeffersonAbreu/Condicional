package jeff.model.domain;

import java.io.Serializable;

import jeff.util.Util;

public class ItensCondicional implements Serializable {
    private Condicional condicional;
    private Roupa roupa;
    private double valorUni = 0.0;
    private int qtd = 0;
    private double valorTotal = 0.0;

    public ItensCondicional() {
    }

    public ItensCondicional(Condicional condicional, Roupa roupa, double valor_uni, int qtd, double valor_total) {
        this.condicional = condicional;
        this.roupa = roupa;
        this.valorUni = valor_uni;
        this.qtd = qtd;
        this.valorTotal = valor_total;
    }

    public Integer getId() {
        return getRoupa().getId();
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

    public double getValorUniDouble() {
        return valorUni;
    }

    public String getValorUni() {
        return Util.toStringDinheiro(valorUni);
    }

    public void setValorUni(double valor_uni) {
        this.valorUni = valor_uni;
        this.valorTotal = qtd * valorUni;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
        this.valorTotal = qtd * valorUni;
    }

    public double getValorTotalDouble() {
        return valorTotal;
    }

    public String getValorTotal() {
        return Util.toStringDinheiro(valorTotal);
    }

    public void setValorTotal(double valor_total) {
        this.valorTotal = valor_total;
    }

    public String getNomeRoupa() {
        return roupa.getNome();
    }

    @Override
    public String toString() {
        return "ItensCondicional [condicional=" + condicional + ", qtd=" + qtd + ", roupa=" + roupa + ", valor_total="
                + getValorTotal() + ", valor_uni=" + getValorUni() + "]";
    }

}
