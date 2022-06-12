package jeff.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jeff.util.Util;

public class Condicional implements Serializable {
    private int id;
    private Cliente cliente = null;
    private Atendente atendente = null;
    private LocalDate data;
    private double valor;
    private int qtd;
    private boolean ativo;
    // outros
    private List<ItensCondicional> itensDeCondicional = new ArrayList<ItensCondicional>();

    public Condicional() {
        this.id = 0;
        this.setData(LocalDate.now());
        this.setValor(0.0);
        this.setQtd(0);
        this.setAtivo(true);
    }

    public Condicional(int id, Cliente cliente, Atendente atendente, LocalDate data, double valor, int qtd,
            boolean status) {
        this.id = id;
        this.cliente = cliente;
        this.atendente = atendente;
        this.data = data;
        this.valor = valor;
        this.qtd = qtd;
        this.ativo = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDataString() {
        return Util.parseString(data);
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<ItensCondicional> getItensCondicional() {
        return itensDeCondicional;
    }

    public void setItensCondicional(List<ItensCondicional> itensDeCondicional) {
        this.itensDeCondicional = itensDeCondicional;
    }

    public String getNomeCliente() {
        return cliente.getNome();
    }

    public String getNomeAtendente() {
        return atendente.getNome();
    }

    @Override
    public String toString() {
        return "Condicional : id =" + id + "\n\tatendente = " + getAtendente().getKey() + "\n\t cliente="
                + getCliente().getKey() + "\n[ data=" + data
                + ", qtd=" + qtd + ", ativo=" + ativo + ", valor=" + valor + "]";
    }

    public void updateItens(List<ItensCondicional> itensNovos) {
        Integer qtd = 0;
        Double valorTotal = 0.0;
        this.itensDeCondicional = new ArrayList<>();

        for (ItensCondicional item : itensNovos) {
            this.itensDeCondicional.add(item);
            qtd += item.getQtd();
            valorTotal += item.getValorTotalDouble();
        }
        this.setQtd(qtd);
        this.setValor(valorTotal);
    }
}
