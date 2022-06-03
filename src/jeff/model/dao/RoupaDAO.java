package jeff.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeff.model.domain.Roupa;
import jeff.util.SQLs;

public class RoupaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Roupa roupa) {
        String sql = SQLs.INSERT(Roupa.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, roupa.getNome());
            stmt.setDouble(2, roupa.getValorDouble());
            stmt.setInt(3, roupa.getQtd());
            stmt.setInt(4, roupa.getQtd_em_condicional());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoupaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Roupa roupa) {
        String sql = SQLs.UPDATE(Roupa.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, roupa.getNome());
            stmt.setDouble(2, roupa.getValorDouble());
            stmt.setInt(3, roupa.getQtd());
            stmt.setInt(4, roupa.getQtd_em_condicional());
            stmt.setInt(5, roupa.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoupaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Roupa roupa) {
        String sql = SQLs.DELETE(Roupa.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, roupa.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoupaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Roupa> listar() {
        String sql = SQLs.SELECT_ALL(Roupa.class.getSimpleName().toUpperCase());
        List<Roupa> roupas = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Roupa roupa = new Roupa();
                roupa.setId(resultado.getInt("id_roupa"));
                roupa.setNome(resultado.getString("nome"));
                roupa.setValor(resultado.getDouble("valor"));
                roupa.setQtd(resultado.getInt("qtd"));
                roupa.setQtd_em_condicional(resultado.getInt("qtd_em_condicional"));
                roupas.add(roupa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoupaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roupas;
    }

    public Roupa buscar(Roupa roupa) {
        String sql = SQLs.SELECT(Roupa.class.getSimpleName().toUpperCase());
        Roupa retorno = new Roupa();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, roupa.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setId(resultado.getInt("id_roupa"));
                retorno.setNome(resultado.getString("nome"));
                retorno.setValor(resultado.getDouble("valor"));
                retorno.setQtd(resultado.getInt("qtd"));
                retorno.setQtd_em_condicional(resultado.getInt("qtd_em_condicional"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoupaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
