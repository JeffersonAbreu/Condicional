package jeff.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jeff.util.Util;

public class Condicional implements Serializable {
    private int id;
    private Cliente cliente;
    private Atendente atendente;
    private LocalDate data;
    private double valor;
    private int qtd;
    private boolean ativo;
    // outros
    private List<ItensCondicional> itensDeCondicional;

    public Condicional() {
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
    public String getDataString(){
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

    public List<ItensCondicional> getItensDeCondicional() {
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
        return "Condicional [atendente=" + atendente + ", cliente=" + cliente + ", data=" + data + ", id=" + id
                + ", qtd=" + qtd + ", ativo=" + ativo + ", valor=" + valor + "]";
    }

}
