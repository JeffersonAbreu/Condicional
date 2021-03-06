package jeff.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeff.model.domain.Cliente;
import jeff.util.SQLs;

public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sql = SQLs.INSERT(Cliente.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setDate(2, Date.valueOf(cliente.getData_nascimento()));
            // stmt.setString(2, cliente.getData_nascimento().toString());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getCelular());
            stmt.setString(7, cliente.getLogradouro());
            stmt.setString(8, cliente.getBairro());
            stmt.setString(9, cliente.getCidade());
            stmt.setString(10, cliente.getUF());
            stmt.setString(11, cliente.getCep());
            stmt.setDouble(12, cliente.getLimiteDouble());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = SQLs.UPDATE(Cliente.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            // stmt.setString(2, cliente.getData_nascimento().toString());
            stmt.setDate(2, Date.valueOf(cliente.getData_nascimento()));
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getCelular());
            stmt.setString(7, cliente.getLogradouro());
            stmt.setString(8, cliente.getBairro());
            stmt.setString(9, cliente.getCidade());
            stmt.setString(10, cliente.getUF());
            stmt.setString(11, cliente.getCep());
            stmt.setDouble(12, cliente.getLimiteDouble());
            stmt.setInt(13, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = SQLs.DELETE(Cliente.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listar() {
        String sql = SQLs.SELECT_ALL(Cliente.class.getSimpleName().toUpperCase());
        List<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                clientes.add(getCliente(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    public Cliente buscar(Cliente cliente) {
        String sql = SQLs.SELECT(Cliente.class.getSimpleName().toUpperCase());
        Cliente retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = getCliente(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Cliente> listarTodosComConticionalAtiva() {
        String sql = "SELECT cl.* FROM condicional con INNER JOIN cliente cl "
                + "ON cl.id_cliente = con.id_cliente WHERE cl.id_cliente = con.id_cliente "
                + "AND con.ativo = 1 GROUP BY cl.id_cliente ORDER BY cl.id_cliente;";
        List<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                clientes.add(getCliente(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    private Cliente getCliente(ResultSet resultado) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultado.getInt("id_cliente"));
        cliente.setNome(resultado.getString("nome"));
        cliente.setData_nascimento(resultado.getDate("data_nascimento").toLocalDate());
        cliente.setCpf(resultado.getString("cpf"));
        cliente.setEmail(resultado.getString("email"));
        cliente.setTelefone(resultado.getString("telefone"));
        cliente.setCelular(resultado.getString("celular"));
        cliente.setLogradouro(resultado.getString("logradouro"));
        cliente.setBairro(resultado.getString("bairro"));
        cliente.setCidade(resultado.getString("cidade"));
        cliente.setUF(resultado.getString("uf"));
        cliente.setCep(resultado.getString("cep"));
        cliente.setLimite(resultado.getDouble("limite"));
        return cliente;
    }
}
