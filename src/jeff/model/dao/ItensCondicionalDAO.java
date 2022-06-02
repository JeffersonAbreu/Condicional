package jeff.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jeff.model.domain.Condicional;
import jeff.model.domain.ItensCondicional;
import jeff.model.domain.Roupa;
import jeff.util.SQLs;

public class ItensCondicionalDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItensCondicional itensCondicional) {
        String sql = SQLs.INSERT(ItensCondicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensCondicional.getCondicional().getID());
            stmt.setInt(2, itensCondicional.getRoupa().getID());
            stmt.setDouble(3, itensCondicional.getValorUni());
            stmt.setInt(4, itensCondicional.getQtd());
            stmt.setDouble(5, itensCondicional.getValorTotal());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensCondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ItensCondicional itensCondicional) {
        String sql = SQLs.UPDATE(ItensCondicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, itensCondicional.getValorUni());
            stmt.setInt(2, itensCondicional.getQtd());
            stmt.setDouble(3, itensCondicional.getValorTotal());
            stmt.setInt(5, itensCondicional.getCondicional().getID());
            stmt.setInt(6, itensCondicional.getRoupa().getID());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensCondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ItensCondicional itensCondicional) {
        String sql = SQLs.DELETE(ItensCondicional.class.getSimpleName().toUpperCase());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensCondicional.getCondicional().getID());
            stmt.setInt(2, itensCondicional.getRoupa().getID());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensCondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ItensCondicional> listar() {
        String sql = SQLs.SELECT_ALL(ItensCondicional.class.getSimpleName().toUpperCase());
        List<ItensCondicional> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItensCondicional itensCondicional = new ItensCondicional();
                Condicional condicional = new Condicional();
                Roupa roupa = new Roupa();

                condicional.setID(resultado.getInt("id_condicional"));

                roupa.setID(resultado.getInt("id_roupa"));

                itensCondicional.setValorUni(resultado.getDouble("valor_uni"));
                itensCondicional.setQtd(resultado.getInt("qtd"));
                itensCondicional.setValorTotal(resultado.getDouble("valor_total"));

                CondicionalDAO condicionalDAO = new CondicionalDAO();
                condicionalDAO.setConnection(connection);
                condicional = condicionalDAO.buscar(condicional);

                // Obtendo os dados completos do Roupa associado ao Item de Condicional
                RoupaDAO roupaDAO = new RoupaDAO();
                roupaDAO.setConnection(connection);
                roupa = roupaDAO.buscar(roupa);

                itensCondicional.setCondicional(condicional);
                itensCondicional.setRoupa(roupa);

                retorno.add(itensCondicional);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensCondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<ItensCondicional> listarPorCondicional(Condicional cond) {
        System.out.println(ItensCondicional.class.getSimpleName().toUpperCase());
        String sql = SQLs.SELECT_FK(ItensCondicional.class.getSimpleName().toUpperCase());
        List<ItensCondicional> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cond.getID());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItensCondicional itensCondicional = new ItensCondicional();
                Condicional condicional = new Condicional();
                Roupa roupa = new Roupa();

                condicional.setID(resultado.getInt("id_condicional"));

                roupa.setID(resultado.getInt("id_roupa"));

                itensCondicional.setValorUni(resultado.getDouble("valor_uni"));
                itensCondicional.setQtd(resultado.getInt("qtd"));
                itensCondicional.setValorTotal(resultado.getDouble("valor_total"));

                // CondicionalDAO condicionalDAO = new CondicionalDAO();
                // condicionalDAO.setConnection(connection);
                // condicional = condicionalDAO.buscar(condicional);

                // Obtendo os dados completos do Roupa associado ao Item de Condicional
                RoupaDAO roupaDAO = new RoupaDAO();
                roupaDAO.setConnection(connection);
                roupa = roupaDAO.buscar(roupa);

                itensCondicional.setCondicional(condicional);
                itensCondicional.setRoupa(roupa);

                retorno.add(itensCondicional);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensCondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ItensCondicional buscar(ItensCondicional itensCond) {
        String sql = SQLs.SELECT(ItensCondicional.class.getSimpleName().toUpperCase());
        ItensCondicional retorno = new ItensCondicional();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensCond.getCondicional().getID());
            stmt.setInt(2, itensCond.getRoupa().getID());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                ItensCondicional itensCondicional = new ItensCondicional();
                Condicional condicional = new Condicional();
                Roupa roupa = new Roupa();

                condicional.setID(resultado.getInt("id_condicional"));

                roupa.setID(resultado.getInt("id_roupa"));

                itensCondicional.setValorUni(resultado.getDouble("valor_uni"));
                itensCondicional.setQtd(resultado.getInt("qtd"));
                itensCondicional.setValorTotal(resultado.getDouble("valor_total"));

                CondicionalDAO condicionalDAO = new CondicionalDAO();
                condicionalDAO.setConnection(connection);
                condicional = condicionalDAO.buscar(condicional);

                // Obtendo os dados completos do Roupa associado ao Item de Condicional
                RoupaDAO roupaDAO = new RoupaDAO();
                roupaDAO.setConnection(connection);
                roupa = roupaDAO.buscar(roupa);

                itensCondicional.setCondicional(condicional);
                itensCondicional.setRoupa(roupa);

                retorno = itensCondicional;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CondicionalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
