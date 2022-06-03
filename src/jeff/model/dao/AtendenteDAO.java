package jeff.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeff.model.domain.Atendente;
import jeff.util.SQLs;

public class AtendenteDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Atendente> listar() {
        String sql = SQLs.SELECT_ALL(Atendente.class.getSimpleName().toUpperCase());
        List<Atendente> atendentes = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Atendente atendente = new Atendente();
                atendente.setId(resultado.getInt("id_atendente"));
                atendente.setNome(resultado.getString("nome"));
                atendente.setLogin(resultado.getString("login"));
                atendente.setSenha(resultado.getString("senha"));
                atendentes.add(atendente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtendenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atendentes;
    }

    public Atendente buscar(Atendente atendente) {
        String sql = SQLs.SELECT(Atendente.class.getSimpleName().toUpperCase());
        Atendente retorno = new Atendente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, atendente.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                atendente.setId(resultado.getInt("id_atendente"));
                atendente.setNome(resultado.getString("nome"));
                atendente.setLogin(resultado.getString("login"));
                atendente.setSenha(resultado.getString("senha"));
                retorno = atendente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AtendenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
