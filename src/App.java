import java.sql.Connection;

import jeff.model.dao.ClienteDAO;
import jeff.model.dao.CondicionalDAO;
import jeff.model.database.Database;
import jeff.model.database.DatabaseFactory;
import jeff.model.domain.Cliente;
import jeff.model.domain.Condicional;
import jeff.util.SQLs;

public class App {
    public static void main(String[] args) {
        System.console();
        Database database = DatabaseFactory.getDatabase(DatabaseFactory.SQLite);
        Connection connection = database.conectar();
        Condicional condicional = new Condicional();
        SQLs.start();
        condicional.setId(1);
        CondicionalDAO condicionalDAO = new CondicionalDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.setConnection(connection);
        condicionalDAO.setConnection(connection);
        // condicionalDAO.inserir(c);
        // System.out.println(condicionalDAO.buscar(condicional));
        for (Cliente cliente : clienteDAO.listar()) {
            System.out.println(cliente);
        }
    }
}