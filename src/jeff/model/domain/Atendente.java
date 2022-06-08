package jeff.model.domain;

import java.io.Serializable;

public class Atendente implements Serializable {
    private int id;
    private String nome;
    private String login;
    private String senha;

    public Atendente() {
    }

    public Atendente(int id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Atendente [id=" + id + ", login=" + login + ", nome=" + nome + ", senha=" + senha + "]";
    }

    public String getKey() {
        return getId() + " : " + getNome();
    }

    public void setKey(String key) {
        String[] keyArray = key.split(" : ");
        setId(Integer.parseInt(keyArray[0]));
        setNome(keyArray[1]);
    }
}
