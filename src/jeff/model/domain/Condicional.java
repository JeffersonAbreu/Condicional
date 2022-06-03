package jeff.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
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

    @Override
    public String toString() {
        return "Condicional [atendente=" + atendente + ", cliente=" + cliente + ", data=" + data + ", id=" + id
                + ", qtd=" + qtd + ", ativo=" + ativo + ", valor=" + valor + "]";
    }

}
