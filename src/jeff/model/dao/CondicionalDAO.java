package jeff.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeff.model.domain.Atendente;
import jeff.model.domain.Cliente;
import jeff.model.domain.Condicional;
import jeff.model.domain.ItensCondicional;
import jeff.util.SQLs;

public class CondicionalDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Condicional condicional) {
        String sql = SQLs.INSERT(Condicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, condicional.getCliente().getId());
            stmt.setInt(2, condicional.getAtendente().getId());
            stmt.setDate(3, Date.valueOf(condicional.getData()));
            stmt.setDouble(4, condicional.getValorDouble());
            stmt.setInt(5, condicional.getQtd());
            stmt.setBoolean(6, condicional.isAtivo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Condicional condicional) {
        String sql = SQLs.UPDATE(Condicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, condicional.getCliente().getId());
            stmt.setInt(2, condicional.getAtendente().getId());
            stmt.setDate(3, Date.valueOf(condicional.getData()));
            stmt.setDouble(4, condicional.getValorDouble());
            stmt.setInt(5, condicional.getQtd());
            stmt.setBoolean(6, condicional.isAtivo());
            stmt.setInt(7, condicional.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Condicional condicional) {
        String sql = SQLs.DELETE(Condicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, condicional.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Condicional> listarPorNomeCliente(String nome) {
        List<Condicional> retorno = new ArrayList<>();
        String sql;
        PreparedStatement stmt;
        try {
            sql = SQLs.SELECT_ALL(Condicional.class.getSimpleName().toUpperCase());
            stmt = connection.prepareStatement(sql);
            if (nome != null && !nome.isEmpty() && !nome.trim().isEmpty()) {
                sql = sql.replace(";", " WHERE id_cliente IN (SELECT id_cliente FROM CLIENTE WHERE nome LIKE '");
                if (nome.startsWith(" ")) {
                    nome = nome.substring(1);
                    sql += nome + "%');";
                } else {
                    sql += "%" + nome + "%');";
                }
                stmt = connection.prepareStatement(sql);
            }
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Condicional condicional = new Condicional();
                Atendente atendente = new Atendente();
                Cliente cliente = new Cliente();
                List<ItensCondicional> itensCondicional = new ArrayList<>();

                condicional.setId(resultado.getInt("id_condicional"));
                cliente.setId(resultado.getInt("id_cliente"));
                atendente.setId(resultado.getInt("id_atendente"));
                condicional.setData(resultado.getDate("data_hora").toLocalDate());
                condicional.setValor(resultado.getDouble("valor"));
                condicional.setQtd(resultado.getInt("qtd"));
                condicional.setAtivo(resultado.getBoolean("ativo"));

                // Obtendo os dados completos do Cliente associado à Condicional
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);

                // Obtendo Atendente
                AtendenteDAO atendenteDAO = new AtendenteDAO();
                atendenteDAO.setConnection(connection);
                atendente = atendenteDAO.buscar(atendente);

                // Obtendo os dados completos dos Itens de Condicional associados à Condicional
                ItensCondicionalDAO itensCondicionalDAO = new ItensCondicionalDAO();
                itensCondicionalDAO.setConnection(connection);
                itensCondicional = itensCondicionalDAO.listarPorCondicional(condicional);

                condicional.setCliente(cliente);
                condicional.setAtendente(atendente);
                condicional.setItensCondicional(itensCondicional);
                retorno.add(condicional);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Condicional> listar() {
        String sql = SQLs.SELECT_ALL(Condicional.class.getSimpleName().toUpperCase());
        List<Condicional> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Condicional condicional = new Condicional();
                Atendente atendente = new Atendente();
                Cliente cliente = new Cliente();
                List<ItensCondicional> itensCondicional = new ArrayList<>();

                condicional.setId(resultado.getInt("id_condicional"));
                cliente.setId(resultado.getInt("id_cliente"));
                atendente.setId(resultado.getInt("id_atendente"));
                condicional.setData(resultado.getDate("data_hora").toLocalDate());
                condicional.setValor(resultado.getDouble("valor"));
                condicional.setQtd(resultado.getInt("qtd"));
                condicional.setAtivo(resultado.getBoolean("ativo"));

                // Obtendo os dados completos do Cliente associado à Condicional
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);

                // Obtendo Atendente
                AtendenteDAO atendenteDAO = new AtendenteDAO();
                atendenteDAO.setConnection(connection);
                atendente = atendenteDAO.buscar(atendente);

                // Obtendo os dados completos dos Itens de Condicional associados à Condicional
                ItensCondicionalDAO itensCondicionalDAO = new ItensCondicionalDAO();
                itensCondicionalDAO.setConnection(connection);
                itensCondicional = itensCondicionalDAO.listarPorCondicional(condicional);

                condicional.setCliente(cliente);
                condicional.setAtendente(atendente);
                condicional.setItensCondicional(itensCondicional);
                retorno.add(condicional);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Condicional buscar(Condicional condicional) {
        String sql = SQLs.SELECT(Condicional.class.getSimpleName().toUpperCase());
        Condicional retorno = new Condicional();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, condicional.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Atendente atendente = new Atendente();
                Cliente cliente = new Cliente();

                condicional.setId(resultado.getInt("id_condicional"));
                cliente.setId(resultado.getInt("id_cliente"));
                atendente.setId(resultado.getInt("id_atendente"));
                condicional.setData(resultado.getDate("data_hora").toLocalDate());
                condicional.setValor(resultado.getDouble("valor"));
                condicional.setQtd(resultado.getInt("qtd"));
                condicional.setAtivo(resultado.getBoolean("ativo"));

                // Obtendo os dados completos do Cliente associado à Condicional
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);

                // Obtendo Atendente
                AtendenteDAO atendenteDAO = new AtendenteDAO();
                atendenteDAO.setConnection(connection);
                atendente = atendenteDAO.buscar(atendente);

                condicional.setCliente(cliente);
                condicional.setAtendente(atendente);
                retorno = condicional;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Condicional buscarUltimaCondicional() {
        String sql = "SELECT max(id_condicional) FROM condicional";
        Condicional retorno = new Condicional();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setId(resultado.getInt("max"));
                return retorno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList> listarQuantidadeCondicionalsPorMes() {
        String sql = "select count(id_condicional), extract(year from data_hora) as ano, extract(month from data_hora) as mes from condicional group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap<Integer, ArrayList>();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList<>();
                if (!retorno.containsKey(resultado.getInt("ano"))) {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                } else {
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
