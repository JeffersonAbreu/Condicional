package jeff.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

import jeff.util.Util;

public class Cliente implements Serializable {

    private int id;
    private String nome;
    private LocalDate data_nascimento;
    private String cpf;
    private String email;
    private String telefone;
    private String celular;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private double limite;

    public Cliente() {
    }

    public Cliente(int id, String nome, LocalDate data_nascimento, String cpf, String email, String telefone,
            String celular, String logradouro, String bairro, String cidade, String uf, String cep, double limite) {
        this.id = id;
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.limite = limite;
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

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return uf;
    }

    public void setUF(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public double getLimiteDouble() {
        return limite;
    }

    public String getLimite() {
        return Util.toStringDinheiro(limite);
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public String toString() {
        return "Cliente [bairro=" + bairro + ", celular=" + celular + ", cep=" + cep + ", cidade=" + cidade + ", cpf="
                + cpf + ", data_nascimento=" + data_nascimento + ", email=" + email + ", id=" + id + ", limite="
                + limite + ", logradouro=" + logradouro + ", nome=" + nome + ", telefone=" + telefone + ", uf=" + uf
                + "]";
    }

}
